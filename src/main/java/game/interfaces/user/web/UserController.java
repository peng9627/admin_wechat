package game.interfaces.user.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import game.application.commissiondetails.ICommissionDetailsAppService;
import game.application.commissiondetails.command.CreateCommand;
import game.application.user.IUserAppService;
import game.application.user.IUserHistoryConsumptionAppService;
import game.application.user.IUserParentAppService;
import game.application.user.representation.UserRepresentation;
import game.core.common.Constants;
import game.core.enums.FlowType;
import game.core.util.CoreDateUtils;
import game.core.util.CoreHttpUtils;
import game.core.util.CoreStringUtils;
import game.domain.model.user.UserHistoryConsumption;
import game.domain.model.user.UserParent;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Author pengyi
 * Date 17-5-25.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseApiController {

    private final IUserAppService userAppService;
    private final IUserParentAppService userParentAppService;
    private final ICommissionDetailsAppService commissionDetailsAppService;
    private final IUserHistoryConsumptionAppService userHistoryConsumptionAppService;

    private final Map<Integer, UserParent> userParentMap = new HashMap<>();
    private final Map<Integer, List<UserParent>> parent_userParent = new HashMap<>();
    private final List<CreateCommand> createCommands = new ArrayList<>();
    private final List<UserHistoryConsumption> userHistoryConsumptions = new ArrayList<>();
    private final Map<String, BigDecimal> updateCommands = new HashMap<>();
    private final Map<Integer, BigDecimal> allCommission = new HashMap<>();

    @Autowired
    public UserController(IUserAppService userAppService, IUserParentAppService userParentAppService, ICommissionDetailsAppService commissionDetailsAppService, IUserHistoryConsumptionAppService userHistoryConsumptionAppService) {
        this.userAppService = userAppService;
        this.userParentAppService = userParentAppService;
        this.commissionDetailsAppService = commissionDetailsAppService;
        this.userHistoryConsumptionAppService = userHistoryConsumptionAppService;
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

            //汇友
            response.sendRedirect(Constants.MANAGER_URL + "mobile/index?accountId=" + userRepresentation.getUserId() + "&key=" + CoreStringUtils.md5(userRepresentation.getUserId() + "iLMkYhwosr7FDczW", 32, false, "utf-8"));
//            response.sendRedirect("https://fir.im/xunmi");
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
                System.out.println("返利错误重新返利" + i);
            }
            if (i >= 100) {
                jsonMessage.setData(1);
                notsave = false;
                logger.error("返利数据", jsonArray);
            }
        }

        return jsonMessage;
    }

    @RequestMapping(value = "/set_lastday_rebate")
    @ResponseBody
    public JsonMessage setLastDayRebate() {
        long a = System.currentTimeMillis();
        JsonMessage jsonMessage = new JsonMessage();
        boolean notsave = true;
        int i = 0;
        while (notsave) {
            try {
                jsonMessage.setCode(0);
                userParentAppService.lastDayRebate();
                notsave = false;
            } catch (Exception e) {
                i++;
                logger.error("昨日返利修改失败" + i + e.getMessage(), e);
            }
            if (i >= 100) {
                jsonMessage.setData(1);
                notsave = false;
            }
        }
        lastDayRebate();
        System.out.println(System.currentTimeMillis() - a);
        return jsonMessage;
    }

    @RequestMapping(value = "/lastday_rebate")
    @ResponseBody
    public JsonMessage lastDayRebate() {
        JsonMessage jsonMessage = new JsonMessage();
        boolean notsave = true;
        int i = 0;
        while (notsave) {
            try {
                userParentMap.clear();
                parent_userParent.clear();
                createCommands.clear();
                userHistoryConsumptions.clear();
                updateCommands.clear();
                allCommission.clear();
                List<UserParent> userParents = userParentAppService.allUserParent();
                for (UserParent userParent : userParents) {
                    userParentMap.put(userParent.getUserId(), userParent);
                    if (null != userParent.getParent()) {
                        if (!parent_userParent.containsKey(userParent.getParent())) {
                            parent_userParent.put(userParent.getParent(), new ArrayList<>());
                        }
                        parent_userParent.get(userParent.getParent()).add(userParent);
                    }
                }

                List<Integer> integers = userParentAppService.daqu();
                for (Integer integer : integers) {
                    lastDayCommission(integer, null);
                }

                commissionDetailsAppService.createAll(createCommands);
                userHistoryConsumptionAppService.createAll(userHistoryConsumptions);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean notsave = true;
                        int i = 0;
                        while (notsave) {
                            try {
                                userParentAppService.addAllCommission(updateCommands);
                                notsave = false;
                            } catch (Exception e) {
                                i++;
                                logger.error("昨日返利保存失败" + e.getMessage(), e);
                            }
                            if (i >= 100) {
                                notsave = false;
                            }
                        }
                    }
                }).start();

                notsave = false;
                jsonMessage.setCode(0);
            } catch (Exception e) {
                i++;
                logger.error("昨日返利计算失败" + i + e.getMessage(), e);
            }
            if (i >= 100) {
                jsonMessage.setData(1);
                notsave = false;
            }
        }
        return jsonMessage;
    }

    @RequestMapping(value = "/lastday_rebate_commission")
    @ResponseBody
    public BigDecimal lastDayCommission(Integer userId, Integer parentId) {
        BigDecimal commission = BigDecimal.ZERO;//给上级反的
        long time = System.currentTimeMillis();
        try {
            UserParent userParent = userParentMap.get(userId);
            UserParent parent = userParentMap.get(parentId);
            BigDecimal total = userParent.getLastDayCommission();//当前玩家

            BigDecimal selfAllCommission = BigDecimal.ZERO;

            List<UserParent> userParents = parent_userParent.get(userId);
            if (null != userParents && userParents.size() > 0) {
                for (UserParent userParent1 : userParents) {

                    BigDecimal g = lastDayCommission(userParent1.getUserId(), userId);
                    total = total.add(g).setScale(2, RoundingMode.HALF_UP);

                    BigDecimal sanji = updateCommands.get(userParent1.getId());
                    if (null == sanji) {
                        sanji = BigDecimal.ZERO;
                    }
                    sanji = sanji.add(userParent1.getLastDayCommission());
                    commission = commission.add(sanji.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP));

                    //下下级给自己反的
                    if (null != parentId && 0 != g.setScale(2, RoundingMode.HALF_UP).doubleValue()) {
                        CreateCommand createCommand = new CreateCommand();
                        createCommand.setFlowType(FlowType.IN_FLOW);
                        createCommand.setUserId(parentId);
                        createCommand.setMoney(sanji.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP));
                        createCommand.setDescription(userParent1.getUserId() + "返利" + sanji + "佣金");
                        createCommand.setFromUser(userParent1.getUserId());
                        createCommands.add(createCommand);

                        if (0 != commission.doubleValue()) {
                            if (!updateCommands.containsKey(parent.getId())) {
                                updateCommands.put(parent.getId(), BigDecimal.ZERO);
                            }
                            updateCommands.replace(parent.getId(), updateCommands.get(parent.getId()).add(createCommand.getMoney()));
                        }
                    }

                    if (allCommission.containsKey(userParent1.getUserId())) {
                        selfAllCommission = selfAllCommission.add(allCommission.get(userParent1.getUserId()));
                    }
                }
            }
            commission = commission.add(total.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP));

            selfAllCommission = selfAllCommission.add(total);
            allCommission.put(userId, selfAllCommission);

            //直属下级给自己反的
            if (null != parentId) {
                if (0 != total.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP).doubleValue()) {
                    CreateCommand createCommand = new CreateCommand();
                    createCommand.setFlowType(FlowType.IN_FLOW);
                    createCommand.setUserId(parentId);
                    createCommand.setMoney(total.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP));
                    createCommand.setDescription(userParent.getUserId() + "返利" + total.doubleValue() + "佣金");
                    createCommand.setFromUser(userParent.getUserId());
                    createCommands.add(createCommand);
                    if (!updateCommands.containsKey(parent.getId())) {
                        updateCommands.put(parent.getId(), BigDecimal.ZERO);
                    }
                    updateCommands.replace(parent.getId(), updateCommands.get(parent.getId()).add(createCommand.getMoney()));
                }
            }

            userHistoryConsumptions.add(new UserHistoryConsumption(CoreDateUtils.addDay(new Date(), -1), userId, total, userParent.getLastDayConsumption(), selfAllCommission));

            logger.info("parent" + parentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return commission;
    }
}
