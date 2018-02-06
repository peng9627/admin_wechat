package game.domain.model.user;

/**
 * 用户
 * Created by pengyi on 2016/4/15.
 */
public class User {

    private Integer userId;             //用户id
    private String account;             //帐号

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
}
