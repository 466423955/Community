package hasaki.community.model;

/**
 * Create by hanzp on 2020-02-27
 */
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreatetime;
    private Long gmtModifytime;
    private String thirdParty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGmtCreatetime() {
        return gmtCreatetime;
    }

    public void setGmtCreatetime(Long gmtCreatetime) {
        this.gmtCreatetime = gmtCreatetime;
    }

    public Long getGmtModifytime() {
        return gmtModifytime;
    }

    public void setGmtModifytime(Long gmtModifytime) {
        this.gmtModifytime = gmtModifytime;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thridParty) {
        this.thirdParty = thridParty;
    }
}
