package game.application.user;

import com.alibaba.fastjson.JSONObject;
import game.application.user.representation.UserRepresentation;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
public interface IUserAppService {

    UserRepresentation loginAndBindParent(JSONObject userinfoJson);

    UserRepresentation byUserId(int parent);
}
