package game.core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import game.application.wechat.WechatUserInfo;
import game.core.common.Constants;
import game.core.util.CoreHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pengyi
 * Date : 17-12-22.
 * desc:
 */
public class WechatApi {
    private static Logger logger = LoggerFactory.getLogger(WechatApi.class);

    public static String get_access_token() {
        String response = CoreHttpUtils.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.WECHAT_APPID + "&secret=" + Constants.WECHAT_APPSECRET, "utf-8");
        logger.info("get_access_token---" + response);
        return null;
    }

    public static JSONObject wechatQRCode() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action_name", "QR_LIMIT_STR_SCENE");

        JSONObject action_info = new JSONObject();
        JSONObject scene = new JSONObject();
        scene.put("scene_str", 111111);
        action_info.put("scene", scene);
        jsonObject.put("action_info", action_info);

        String response = CoreHttpUtils.urlConnection("https://api.weixin.qq.com/cgi-bin/qrcode/create", "access_token=" + jsonObject.toJSONString());
        logger.info("wechatQRCode", response);
        jsonObject = JSON.parseObject(response);
        return jsonObject;
    }

    public static JSONObject getUserInfo(String openid) {
        String response = CoreHttpUtils.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + get_access_token() + "&openid=" + openid + "&lang=zh_CN", "utf-8");
        logger.info("getUserInfo", response);
        return JSON.parseObject(response);
    }
}
