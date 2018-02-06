package game.application.user.representation;

/**
 * Created by pengyi on 2016/4/19.
 */
public class UserParentRepresentation {

    private Integer userId;             //用户id
    private Integer parent;             //上级

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }
}
