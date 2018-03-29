package game.domain.model.user;

/**
 * 用户
 * Created by pengyi on 2016/4/15.
 */
public class User {

    private Integer userId;             //用户id
    private String account;             //帐号
    private Integer card;               //房卡
    private Integer gold;               //金币

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

    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }
}
