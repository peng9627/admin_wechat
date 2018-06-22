package game.interfaces.user.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import game.application.user.IUserAppService;
import game.application.user.IUserParentAppService;
import game.application.user.representation.UserRepresentation;
import game.core.common.Constants;
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
import java.util.List;
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
                String access_token = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.WECHAT_OFFICE_APPID + "&secret=" + Constants.WECHAT_OFFICE_APPSECRET + "&code=" + code + "&grant_type=authorization_code", "utf-8");
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
                        String refresh = CoreHttpUtils.get("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + Constants.WECHAT_OFFICE_APPID + "&grant_type=refresh_token&refresh_token=" + jsonObject.getString("refresh_token"), "utf-8");
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
                logger.info("111");
                response.sendRedirect(Constants.MANAGER_URL + "mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "jhmjg", 32, false, "utf-8"));
                logger.info("222");
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
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
        boolean notsave = true;
        int i = 0;
        while (notsave) {
            try {
                jsonMessage.setCode(0);
                userParentAppService.consumption(JSON.parseArray(jsonArray));
                notsave = false;
            } catch (Exception e) {
                i++;
                logger.error("返利错误重新返利" + i, e);
            }
            if (i >= 100) {
                jsonMessage.setData(1);
                notsave = false;
                logger.error("返利数据" + jsonArray);
            }
        }

        return jsonMessage;
    }

    @RequestMapping(value = "/lastday_rebate")
    @ResponseBody
    public JsonMessage lastDayRebate() {
        JsonMessage jsonMessage = new JsonMessage();
        try {
            jsonMessage.setCode(0);
            long total = System.currentTimeMillis();
            long time = System.currentTimeMillis();
            userParentAppService.lastDayRebate();
            List<Integer> integers = userParentAppService.userIds();
            System.out.println(System.currentTimeMillis() - time);
            time = System.currentTimeMillis();
            for (Integer integer : integers) {
                lastDayRebateCommission(integer);
            }
            System.out.println(System.currentTimeMillis() - time);
            System.out.println(System.currentTimeMillis() - total);
        } catch (Exception e) {
            jsonMessage.setData(1);
            e.printStackTrace();
        }
        return jsonMessage;
    }

    @RequestMapping(value = "/lastday_rebate_commission")
    @ResponseBody
    public JsonMessage lastDayRebateCommission(Integer userId) {
        JsonMessage jsonMessage = new JsonMessage();
        try {
            jsonMessage.setCode(0);
            userParentAppService.lastDayRebateCommission(userId);
        } catch (Exception e) {
            jsonMessage.setData(1);
            e.printStackTrace();
        }
        return jsonMessage;
    }
}
