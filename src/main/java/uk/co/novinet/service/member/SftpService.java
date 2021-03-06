package uk.co.novinet.service.member;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.util.Collections.sort;
import static java.util.Comparator.comparing;

@Service
public class SftpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SftpService.class);

    @Value("${sftpUsername}")
    private String sftpUsername;

    @Value("${sftpPassword}")
    private String sftpPassword;

    @Value("${sftpHost}")
    private String sftpHost;

    @Value("${sftpPort}")
    private Integer sftpPort;

    @Value("${sftpRootDirectory}")
    private String sftpRootDirectory;

    public void removeAllDocsForMember(Member member) {
        LOGGER.info("Going to try and remove all documents for member {}", member);

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
            session.setPassword(sftpPassword);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            String memberSftpRootDirectory = memberRootDirectory(member);

            LOGGER.info("Going to delete member root directory: {}", memberSftpRootDirectory);

            recursiveDirectoryDelete(sftpChannel, memberSftpRootDirectory + "/");
        } catch (Exception e) {
            LOGGER.error("Unable to retrieve documents for member: " + member, e);
            throw new RuntimeException(e);
        } finally {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public void recursiveDirectoryDelete(ChannelSftp channelSftp, String remoteDir) {
        try {
            if (isDirectory(channelSftp, remoteDir)) {
                Vector<ChannelSftp.LsEntry> dirList = channelSftp.ls(remoteDir);

                for (ChannelSftp.LsEntry entry : dirList) {
                    if (!(entry.getFilename().equals(".") || entry.getFilename().equals(".."))) {
                        if (entry.getAttrs().isDir()) {
                            recursiveDirectoryDelete(channelSftp, remoteDir + entry.getFilename() + File.separator);
                        } else {
                            channelSftp.rm(remoteDir + entry.getFilename());
                        }
                    }
                }

                channelSftp.cd("..");
                channelSftp.rmdir(remoteDir);
            }
        } catch (SftpException e) {
            LOGGER.warn("Could not remove member sftp directory: " + remoteDir, e);
        }
    }

    private boolean isDirectory(ChannelSftp channelSftp, String remoteDirectory) throws SftpException {
        return channelSftp.stat(remoteDirectory).isDir();
    }

    public List<SftpDocument> getAllDocumentsForMember(Member member) {
        LOGGER.info("Going to try and retrieve all documents for member {}", member);

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;
        List<SftpDocument> sftpDocuments = new ArrayList<>();

        try {
            session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
            session.setPassword(sftpPassword);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            String memberSftpRootDirectory = memberRootDirectory(member);

            LOGGER.info("Going to list timestamp dirs in member sftp root directory: {}", memberSftpRootDirectory);

            Vector<ChannelSftp.LsEntry> memberTimestampDirectories = null;

            try {
                memberTimestampDirectories = sftpChannel.ls(memberSftpRootDirectory);
            } catch (SftpException e) {
                LOGGER.warn("Directory {} not found. Cound not find documents for member: {}", memberSftpRootDirectory, member);
                return sftpDocuments;
            }

            LOGGER.info("Found memberTimestampDirectories: {}", memberTimestampDirectories);

            for (ChannelSftp.LsEntry timestampSubdirectoryLsEntry : memberTimestampDirectories) {
                if (timestampSubdirectoryLsEntry.getAttrs().isDir()) {
                    String timestampSubdirectory = memberSftpRootDirectory + "/" + timestampSubdirectoryLsEntry.getFilename();

                    Vector<ChannelSftp.LsEntry> memberDocuments = sftpChannel.ls(timestampSubdirectory);
                    LOGGER.info("Found documents: {}", memberDocuments);
                    for (ChannelSftp.LsEntry documentLsEntry : memberDocuments) {
                        if (!documentLsEntry.getAttrs().isDir()) {
                            sftpDocuments.add(new SftpDocument(documentLsEntry.getFilename(), timestampSubdirectory + "/" + documentLsEntry.getFilename(), Instant.ofEpochMilli(Long.parseLong(timestampSubdirectoryLsEntry.getFilename()))));
                        }
                    }
                }
            }

            sort(sftpDocuments, comparing(SftpDocument::getFilename));

            return sftpDocuments;
        } catch (Exception e) {
            LOGGER.error("Unable to retrieve documents for member: " + member, e);
            throw new RuntimeException(e);
        } finally {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    private String memberRootDirectory(Member member) {
        return sftpRootDirectory + "/" + sanitisedEmailAddress(member.getEmailAddress());
    }

    private String sanitisedEmailAddress(String emailAddress) {
        return emailAddress.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    public void downloadDocument(String path, OutputStream out) {
        LOGGER.info("Going to try and retrieving document with path {}", path);

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
            session.setPassword(sftpPassword);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            sftpChannel.get(path, out);
        } catch (Exception e) {
            LOGGER.error("Unable to retrieve document with path: " + path, e);
            throw new RuntimeException(e);
        } finally {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
