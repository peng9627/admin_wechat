package game.application.user.representation;

/**
 * Created by pengyi on 2016/4/19.
 */
public class UserRepresentation {

    private Integer userId;             //用户id
    private String account;             //帐号
    private String bindId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
}
