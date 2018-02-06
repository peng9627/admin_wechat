package game.interfaces.user.api;

import com.alibaba.fastjson.JSONObject;
import game.application.user.IUserAppService;
import game.application.user.IUserParentAppService;
import game.application.user.command.BindCommand;
import game.application.user.representation.UserParentRepresentation;
import game.application.user.representation.UserRepresentation;
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

    @Autowired
    public ApiUserController(IUserAppService userAppService, IUserParentAppService userParentAppService) {
        this.userAppService = userAppService;
        this.userParentAppService = userParentAppService;
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
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", bindCommand.getUserId());
                jsonObject.put("parent", bindCommand.getParent());
                userAppService.loginAndBindParent(jsonObject);
                apiResponse.put("code", 0);
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
            apiResponse.put("data", null == userParent || null == userParent.getParent() ? 0 : userParent.getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }
}
