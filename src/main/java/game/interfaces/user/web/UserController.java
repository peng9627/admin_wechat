package game.interfaces.user.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import game.application.user.IUserAppService;
import game.application.user.IUserParentAppService;
import game.application.user.representation.UserRepresentation;
import game.core.util.CoreHttpUtils;
import game.core.util.CoreStringUtils;
import game.interfaces.shared.api.BaseApiController;
import game.interfaces.shared.web.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Author pengyi
 * Date 17-5-25.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseApiController {

    private final IUserAppService userAppService;
    private final IUserParentAppService userParentAppService;

    @Autowired
    public UserController(IUserAppService userAppService, IUserParentAppService userParentAppService) {
        this.userAppService = userAppService;
        this.userParentAppService = userParentAppService;
    }

    /**
     * 微信登陆
     *
     * @param request Request
     */
    @RequestMapping(value = "/login_wechat")
    public ModelAndView loginWeChat(HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
        UserRepresentation userRepresentation = null;
        try {
            Map<String, String[]> map = request.getParameterMap();
            String code = "";
            String state = "";
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                if (null != entry.getValue()) {
                    for (String s : entry.getValue()) {
                        if (entry.getKey().equals("code")) {
                            code = s;
                        } else if (entry.getKey().equals("state")) {
                            state = s;
                        }
                    }
                }
            }

            JSONObject userinfoJson = new JSONObject();
            if (!CoreStringUtils.isEmpty(code)) {
                //万州
//                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx988152b62477873e&secret=54e1313d48ee0753cec84911a03374ab&code=" + code + "&grant_type=authorization_code", "utf-8");
                //大众
//                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxbb4693c82ec025a5&secret=91738db92b736e00fe8dba8fe1e68ab3&code=" + code + "&grant_type=authorization_code", "utf-8");
                //山城
//                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx2d4606a7e7af7ba9&secret=3834e71b6b4de2d202b2f0409596d0d4&code=" + code + "&grant_type=authorization_code", "utf-8");
                //西北
//                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx24242b2bf731f7d7&secret=81d11ec24e8006c0fbce680da6bbbb2e&code=" + code + "&grant_type=authorization_code", "utf-8");
                //牡丹江
//                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx15468dd1bb6be97f&secret=e452c2420fd5ad0a8e56ff1868f2d928&code=" + code + "&grant_type=authorization_code", "utf-8");
                //江城互娱
//                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx5f92b5138fa881da&secret=38dccbd677ff14f949a95d53ca7c6fd7&code=" + code + "&grant_type=authorization_code", "utf-8");
                //盛天互娱
//                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx8f6bc16c163a4f91&secret=6ecbbb3ee03ae15c07ff6797f429b29d&code=" + code + "&grant_type=authorization_code", "utf-8");
                //全民互娱
                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx34e46b11c9b3db48&secret=c8235fb7ff5f3cbc403f799d27397679&code=" + code + "&grant_type=authorization_code", "utf-8");
                JSONObject jsonObject = JSON.parseObject(access_token);
                if (jsonObject.containsKey("access_token")) {
                    String check = CoreHttpUtils.get("https://api.weixin.qq.com/sns/auth?access_token=" + jsonObject.getString("access_token") + "&openid=" + jsonObject.getString("openid"), "utf-8");
                    System.out.println("check--------" + check);
                    JSONObject checkJson = JSON.parseObject(check);
                    if (checkJson.containsKey("errcode") && 0 == checkJson.getIntValue("errcode")) {
                        String userinfo = CoreHttpUtils.get("https://api.weixin.qq.com/sns/userinfo?access_token=" + jsonObject.getString("access_token") + "&openid=" + jsonObject.getString("openid") + "&lang=zh_CN", "utf-8");
                        userinfoJson = JSON.parseObject(userinfo);
                        System.out.println("userinfoJson--------" + userinfo);
                    } else {
                        //万州
//                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx988152b62477873e&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
                        //大众
//                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wxbb4693c82ec025a5&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
                        //山城
//                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx2d4606a7e7af7ba9&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
                        //西北
//                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx24242b2bf731f7d7&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
                        //牡丹江
//                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx15468dd1bb6be97f&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
                        //江城互娱
//                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx5f92b5138fa881da&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
                        //盛天互娱
//                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx8f6bc16c163a4f91&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
                        //全民互娱
                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx34e46b11c9b3db48&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
                        jsonObject = JSON.parseObject(refresh);
                        String userinfo = CoreHttpUtils.get("https://api.weixin.qq.com/sns/userinfo?access_token=" + jsonObject.getString("access_token") + "&openid=" + jsonObject.getString("openid") + "&lang=zh_CN", "utf-8");
                        userinfoJson = JSON.parseObject(userinfo);
                        System.out.println("userinfoJson--------" + userinfo);
                    }
                }
            }
            if (userinfoJson.containsKey("unionid")) {
                userinfoJson.put("parent", Integer.parseInt(state));
                userRepresentation = userAppService.loginAndBindParent(userinfoJson);
                httpSession.setAttribute("userId", userRepresentation.getUserId());
//                SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteNullListAsEmpty,
//                        SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect,
//                        SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero,
//                        SerializerFeature.WriteNullBooleanAsFalse};
//                int ss = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingName, false);
//                SocketRequest socketRequest = new SocketRequest();
//                socketRequest.setUserId(Integer.parseInt(state));
//                CoreHttpUtils.urlConnectionByRsa("http://127.0.0.1:10410/1", JSON.toJSONString(socketRequest, ss, features));
            }
//            return new ModelAndView("redirect:/user/person");
            if (null != userRepresentation) {
                //万州
//                response.sendRedirect("http://gl.zzjhmjg.com/mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                //大众
//                response.sendRedirect("http://dazhonghuyugl.chuangmikeji.com/mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                //山城
//                response.sendRedirect("http://shanchenghuyugl.chuangmikeji.com/mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                //西北
//                response.sendRedirect("http://gl.zhuanxinyu.com/mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                //西北
//                response.sendRedirect("http://gl.mdjpoker.com/mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                //江城互娱
//                response.sendRedirect("http://gl.jingsaibang.com/mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                //盛天互娱
//                response.sendRedirect("http://gl.zmbaobei.com/mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                //全民互娱
                response.sendRedirect("http://quanmingl.chuangmikeji.com/mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("");
    }

//    @RequestMapping(value = "/person")
//    public ModelAndView person(HttpSession session, HttpServletResponse response) {
//        UserRepresentation userRepresentation = null;
//        try {
//            Integer userId = (Integer) session.getAttribute("userId");
//            if (null != userId) {
//                userRepresentation = userAppService.info(userId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ModelAndView("/person", "userinfo", userRepresentation);
//    }

    @RequestMapping(value = "/child_count/{id}")
    @ResponseBody
    public JsonMessage childCount(@PathVariable String id) {
        JsonMessage jsonMessage = new JsonMessage();
        try {
            jsonMessage.setCode(0);
            jsonMessage.setData(userParentAppService.spreadCount(Integer.valueOf(id)));
        } catch (Exception e) {
            jsonMessage.setData(1);
            e.printStackTrace();
        }
        return jsonMessage;
    }

    @RequestMapping(value = "/consumption")
    @ResponseBody
    public JsonMessage consumption(String jsonArray) {
        JsonMessage jsonMessage = new JsonMessage();
        try {
            jsonMessage.setCode(0);
            userParentAppService.consumption(JSON.parseArray(jsonArray));
        } catch (Exception e) {
            jsonMessage.setData(1);
            e.printStackTrace();
        }
        return jsonMessage;
    }
}
