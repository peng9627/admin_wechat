package game.application.wechat.command;

/**
 * Created by pengyi
 * Date : 17-12-22.
 * desc:
 */
public class WechatMessage {

    private String ToUserName;
    private String Encrypt;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getEncrypt() {
        return Encrypt;
    }

    public void setEncrypt(String encrypt) {
        Encrypt = encrypt;
    }
}
