package game.core.api;

/**
 * Created by pengyi
 * Date : 17-9-10.
 * desc:
 */
public class SocketRequest {

    private int userId;
    private String content;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
