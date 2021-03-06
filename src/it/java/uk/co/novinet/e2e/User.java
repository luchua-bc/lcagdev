package uk.co.novinet.e2e;

public class User {
    private int id;
    private String username;
    private String emailAddress;
    private String name;
    private String token;
    private String mpName;
    private String mpConstituency;
    private String mpParty;
    private Boolean mpEngaged;
    private Boolean mpSympathetic;
    private String schemes;
    private String industry;
    private String howDidYouHearAboutLcag;
    private Boolean memberOfBigGroup;
    private String bigGroupUsername;

    public User(int id, String username, String emailAddress, String name, String token) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.name = name;
        this.token = token;
    }

    public User(int id, String username, String emailAddress, String name, String token, String mpName, String mpConstituency, String mpParty, Boolean mpEngaged, Boolean mpSympathetic, String schemes, String industry, String howDidYouHearAboutLcag, Boolean memberOfBigGroup, String bigGroupUsername) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.name = name;
        this.token = token;
        this.mpName = mpName;
        this.mpConstituency = mpConstituency;
        this.mpParty = mpParty;
        this.mpEngaged = mpEngaged;
        this.mpSympathetic = mpSympathetic;
        this.schemes = schemes;
        this.industry = industry;
        this.howDidYouHearAboutLcag = howDidYouHearAboutLcag;
        this.memberOfBigGroup = memberOfBigGroup;
        this.bigGroupUsername = bigGroupUsername;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public String getMpName() {
        return mpName;
    }

    public String getMpConstituency() {
        return mpConstituency;
    }

    public String getMpParty() {
        return mpParty;
    }

    public Boolean getMpEngaged() {
        return mpEngaged;
    }

    public Boolean getMpSympathetic() {
        return mpSympathetic;
    }

    public String getSchemes() {
        return schemes;
    }

    public String getIndustry() {
        return industry;
    }

    public String getHowDidYouHearAboutLcag() {
        return howDidYouHearAboutLcag;
    }

    public Boolean getMemberOfBigGroup() {
        return memberOfBigGroup;
    }

    public String getBigGroupUsername() {
        return bigGroupUsername;
    }
}
