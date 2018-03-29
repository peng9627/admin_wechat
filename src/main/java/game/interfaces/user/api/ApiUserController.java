package game.interfaces.user.api;

import com.alibaba.fastjson.JSONObject;
import game.application.user.IUserAppService;
import game.application.user.IUserParentAppService;
import game.application.user.command.BindCommand;
import game.application.user.representation.UserParentRepresentation;
import game.application.user.representation.UserRepresentation;
import game.core.pay.GameServer;
import game.core.util.CoreHttpUtils;
import game.core.util.CoreStringUtils;
import game.interfaces.shared.api.BaseApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/user")
public class ApiUserController extends BaseApiController {

    private final IUserAppService userAppService;
    private final IUserParentAppService userParentAppService;
    private final GameServer gameServer;

    @Autowired
    public ApiUserController(IUserAppService userAppService, IUserParentAppService userParentAppService, GameServer gameServer) {
        this.userAppService = userAppService;
        this.userParentAppService = userParentAppService;
        this.gameServer = gameServer;
    }

    @RequestMapping(value = "/bind")
    @ResponseBody
    public JSONObject bind(BindCommand bindCommand) {
        JSONObject apiResponse = new JSONObject();
        apiResponse.put("code", 1);
        try {
            if (0 == bindCommand.getUserId() || 0 == bindCommand.getParent()) {
                return apiResponse;
            }
            UserRepresentation userRepresentation = userAppService.byUserId(bindCommand.getParent());
            if (null == userRepresentation) {
                apiResponse.put("code", 3);
                return apiResponse;
            }
            UserParentRepresentation userParent = userParentAppService.byUserId(bindCommand.getUserId());
            if (null != userParent && null != userParent.getParent()) {
                apiResponse.put("code", 2);
                apiResponse.put("parentId", userParent.getParent().toString());
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", bindCommand.getUserId());
                jsonObject.put("parent", bindCommand.getParent());
                userAppService.loginAndBindParent(jsonObject);
                apiResponse.put("code", 0);
//                //牛英雄
//                jsonObject.clear();
//                jsonObject.put("manager", 998);
//                jsonObject.put("target", bindCommand.getUserId());
//                jsonObject.put("card", 8);
//                jsonObject.put("enc", CoreStringUtils.md5(998 + "&_&" + 0 + "&_&" + bindCommand.getUserId() + "&_&" + 8 + "&_&" + 0 + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
//                String s = CoreHttpUtils.urlConnection(gameServer.getUrl(), "add_card=" + jsonObject.toJSONString());
//                logger.info("绑定送返回" + s);
//
//                jsonObject.clear();
//                jsonObject.put("manager", 998);
//                jsonObject.put("target", bindCommand.getUserId());
//                jsonObject.put("permission", 16);
//                jsonObject.put("enc", CoreStringUtils.md5(998 + "&_&" + bindCommand.getUserId() + "&_&" + 16 + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
//                s = CoreHttpUtils.urlConnection(gameServer.getUrl(), "update_permission=" + jsonObject.toJSONString());
//                logger.info("绑定修改权限返回" + s);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    @RequestMapping(value = "/bind_query")
    @ResponseBody
    public JSONObject bindQuery(BindCommand bindCommand) {
        JSONObject apiResponse = new JSONObject();
        apiResponse.put("code", 1);
        try {
            if (0 == bindCommand.getUserId()) {
                return apiResponse;
            }
            UserParentRepresentation userParent = userParentAppService.byUserId(bindCommand.getUserId());
            apiResponse.put("code", 0);
            apiResponse.put("data", null == userParent || null == userParent.getParent() ? null : userParent.getParent().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }
}
