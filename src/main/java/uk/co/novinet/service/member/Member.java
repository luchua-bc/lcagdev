package uk.co.novinet.service.member;

import uk.co.novinet.auth.MyBbAuthority;
import uk.co.novinet.service.enquiry.PasswordDetails;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class Member {

    private Long id;
    private String emailAddress;
    private String username;
    private String name;
    private String userStatus;
    private String userTwitter;
    private String userTelegram;
    private String userStatusActual;
    private BigDecimal lastPayAmnt;
    private String lastPayDate;
    private String group;
    private List<Long> additionalGroupIds;
    private Instant registrationDate;
    private Boolean hmrcLetterChecked;
    private Boolean identificationChecked;
    private String mpName;
    private String schemes;
    private Boolean mpEngaged;
    private Boolean mpSympathetic;
    private String mpConstituency;
    private String mpParty;
    private Boolean agreedToContributeButNotPaid;
    private String notes;
    private String industry;
    private String token;
    private PasswordDetails passwordDetails;
    private BigDecimal contributionAmount;
    private Boolean hasCompletedMembershipForm;
    private String howDidYouHearAboutLcag;
    private Boolean memberOfBigGroup;
    private String bigGroupUsername;
    private String verifiedBy;
    private Instant verifiedOn;
    private Boolean alreadyHaveAnLcagAccountEmailSent;
    private Boolean registeredForClaim;
    private Boolean hasCompletedClaimParticipantForm;
    private Boolean hasBeenSentClaimConfirmationEmail;
    private Boolean hasOptedOutOfClaim;
    private Boolean sendEmailStatement;
    private String claimToken;
    private String country;
    private String phoneNumber;

    public Member() {}

    public Member(
            Long id,
            String emailAddress,
            String username,
            String name,
            String userStatus,
            String userTwitter,
            String userTelegram,
            String userStatusActual,
            BigDecimal lastPayAmnt,
            String lastPayDate,
            String group,
            List<Long> additionalGroupIds,
            Instant registrationDate,
            Boolean hmrcLetterChecked,
            Boolean identificationChecked,
            String mpName,
            String schemes,
            Boolean mpEngaged,
            Boolean mpSympathetic,
            String mpConstituency,
            String mpParty,
            Boolean agreedToContributeButNotPaid,
            String notes,
            String industry,
            String token,
            Boolean hasCompletedMembershipForm,
            PasswordDetails passwordDetails,
            BigDecimal contributionAmount,
            String howDidYouHearAboutLcag,
            Boolean memberOfBigGroup,
            String bigGroupUsername,
            String verifiedBy,
            Instant verifiedOn,
            Boolean alreadyHaveAnLcagAccountEmailSent,
            Boolean registeredForClaim,
            Boolean hasCompletedClaimParticipantForm,
            Boolean hasBeenSentClaimConfirmationEmail,
            Boolean hasOptedOutOfClaim,
            Boolean sendEmailStatement,
            String claimToken,
            String country,
            String phoneNumber) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.username = username;
        this.userStatus = userStatus;
        this.userTwitter = userTwitter;
        this.userTelegram = userTelegram;
        this.userStatusActual = userStatusActual;
        this.lastPayAmnt = lastPayAmnt;
        this.lastPayDate = lastPayDate;
        this.group = group;
        this.additionalGroupIds = additionalGroupIds;
        this.registrationDate = registrationDate;
        this.hmrcLetterChecked = hmrcLetterChecked;
        this.identificationChecked = identificationChecked;
        this.mpName = mpName;
        this.schemes = schemes;
        this.mpEngaged = mpEngaged;
        this.mpSympathetic = mpSympathetic;
        this.mpConstituency = mpConstituency;
        this.mpParty = mpParty;
        this.agreedToContributeButNotPaid = agreedToContributeButNotPaid;
        this.notes = notes;
        this.industry = industry;
        this.token = token;
        this.hasCompletedMembershipForm = hasCompletedMembershipForm;
        this.passwordDetails = passwordDetails;
        this.name = name;
        this.contributionAmount = contributionAmount;
        this.howDidYouHearAboutLcag = howDidYouHearAboutLcag;
        this.memberOfBigGroup = memberOfBigGroup;
        this.bigGroupUsername = bigGroupUsername;
        this.verifiedBy = verifiedBy;
        this.verifiedOn = verifiedOn;
        this.alreadyHaveAnLcagAccountEmailSent = alreadyHaveAnLcagAccountEmailSent;
        this.registeredForClaim = registeredForClaim;
        this.hasCompletedClaimParticipantForm = hasCompletedClaimParticipantForm;
        this.hasBeenSentClaimConfirmationEmail = hasBeenSentClaimConfirmationEmail;
        this.hasOptedOutOfClaim = hasOptedOutOfClaim;
        this.sendEmailStatement = sendEmailStatement;
        this.claimToken = claimToken;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public PasswordDetails getPasswordDetails() {
        return passwordDetails;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }
    public String getUserStatus() { return userStatus; }
    public String getUserTwitter() {
        return userTwitter;
    }
    public String getUserTelegram() {
        return userTelegram;
    }

    public String getGroup() {
        return group;
    }

    public List<String> getAdditionalGroups() {
        return Arrays.stream(MyBbAuthority.values()).filter(myBbAuthority -> additionalGroupIds.contains(myBbAuthority.getId())).map(MyBbAuthority::getFriendlyName).collect(toList());
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public Boolean getHmrcLetterChecked() {
        return hmrcLetterChecked;
    }

    public Boolean getIdentificationChecked() {
        return identificationChecked;
    }

    public String getMpName() {
        return mpName;
    }

    public String getSchemes() {
        return schemes;
    }

    public Boolean getMpEngaged() {
        return mpEngaged;
    }

    public Boolean getMpSympathetic() {
        return mpSympathetic;
    }

    public String getMpConstituency() {
        return mpConstituency;
    }

    public String getMpParty() {
        return mpParty;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    public void setUserTwitter(String userTwitter) {
        this.userTwitter = userTwitter;
    }
    public void setUserTelegram(String userTelegram) {
        this.userTelegram = userTelegram;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setHmrcLetterChecked(Boolean hmrcLetterChecked) {
        this.hmrcLetterChecked = hmrcLetterChecked;
    }

    public void setIdentificationChecked(Boolean identificationChecked) {
        this.identificationChecked = identificationChecked;
    }

    public void setMpName(String mpName) {
        this.mpName = mpName;
    }

    public void setSchemes(String schemes) {
        this.schemes = schemes;
    }

    public void setMpEngaged(Boolean mpEngaged) {
        this.mpEngaged = mpEngaged;
    }

    public void setMpSympathetic(Boolean mpSympathetic) {
        this.mpSympathetic = mpSympathetic;
    }

    public void setMpConstituency(String mpConstituency) {
        this.mpConstituency = mpConstituency;
    }

    public void setMpParty(String mpParty) {
        this.mpParty = mpParty;
    }

    public Boolean getAgreedToContributeButNotPaid() {
        return agreedToContributeButNotPaid;
    }

    public void setAgreedToContributeButNotPaid(Boolean agreedToContributeButNotPaid) {
        this.agreedToContributeButNotPaid = agreedToContributeButNotPaid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public BigDecimal getContributionAmount() { return contributionAmount; }

    public String getUserStatusActual() { return userStatusActual; }
    public BigDecimal getLastPayAmnt() {
        return lastPayAmnt;
    }
    public String getLastPayDate() { return lastPayDate; }

    public void setContributionAmount(BigDecimal contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public void setUserStatusActual(String userStatusActual) {
        this.userStatusActual = userStatusActual;
    }
    public void setLastPayAmnt(BigDecimal lastPayAmnt) {
        this.lastPayAmnt = lastPayAmnt;
    }
    public void setLastPayDate(String lastPayDate) {
        this.lastPayDate = lastPayDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getHasCompletedMembershipForm() {
        return hasCompletedMembershipForm;
    }

    public void setHasCompletedMembershipForm(Boolean hasCompletedMembershipForm) {
        this.hasCompletedMembershipForm = hasCompletedMembershipForm;
    }

    public String getHowDidYouHearAboutLcag() {
        return howDidYouHearAboutLcag;
    }

    public void setHowDidYouHearAboutLcag(String howDidYouHearAboutLcag) {
        this.howDidYouHearAboutLcag = howDidYouHearAboutLcag;
    }

    public Boolean getMemberOfBigGroup() {
        return memberOfBigGroup;
    }

    public void setMemberOfBigGroup(Boolean memberOfBigGroup) {
        this.memberOfBigGroup = memberOfBigGroup;
    }

    public String getBigGroupUsername() {
        return bigGroupUsername;
    }

    public void setBigGroupUsername(String bigGroupUsername) {
        this.bigGroupUsername = bigGroupUsername;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Instant getVerifiedOn() {
        return verifiedOn;
    }

    public void setVerifiedOn(Instant verifiedOn) {
        this.verifiedOn = verifiedOn;
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }

    public Boolean alreadyHaveAnLcagAccountEmailSent() {
        return alreadyHaveAnLcagAccountEmailSent;
    }

    public Boolean getRegisteredForClaim() {
        return registeredForClaim;
    }

    public void setRegisteredForClaim(Boolean registeredForClaim) {
        this.registeredForClaim = registeredForClaim;
    }

    public Boolean getHasCompletedClaimParticipantForm() {
        return hasCompletedClaimParticipantForm;
    }

    public void setHasCompletedClaimParticipantForm(Boolean hasCompletedClaimParticipantForm) {
        this.hasCompletedClaimParticipantForm = hasCompletedClaimParticipantForm;
    }

    public Boolean getHasBeenSentClaimConfirmationEmail() {
        return hasBeenSentClaimConfirmationEmail;
    }

    public void setHasBeenSentClaimConfirmationEmail(Boolean hasBeenSentClaimConfirmationEmail) {
        this.hasBeenSentClaimConfirmationEmail = hasBeenSentClaimConfirmationEmail;
    }

    public String getClaimToken() {
        return claimToken;
    }

    public void setClaimToken(String claimToken) {
        this.claimToken = claimToken;
    }

    public Boolean getHasOptedOutOfClaim() {
        return hasOptedOutOfClaim;
    }

    public void setHasOptedOutOfClaim(Boolean hasOptedOutOfClaim) {
        this.hasOptedOutOfClaim = hasOptedOutOfClaim;
    }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public List<Long> getAdditionalGroupIds() {
        return additionalGroupIds;
    }

    public void setAdditionalGroupIds(List<Long> additionalGroupIds) {
        this.additionalGroupIds = additionalGroupIds;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getSendEmailStatement() {
        return sendEmailStatement;
    }

    public void setSendEmailStatement(Boolean sendEmailStatement) {
        this.sendEmailStatement = sendEmailStatement;
    }
}
