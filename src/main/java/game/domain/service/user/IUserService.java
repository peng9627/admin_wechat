package game.domain.service.user;

import com.alibaba.fastjson.JSONObject;
import game.application.user.command.EditCommand;
import game.application.user.command.ListCommand;
import game.application.user.command.LoginCommand;
import game.domain.model.user.User;
import game.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
public interface IUserService {

    User searchByUserId(int userId);

    User searchByWechat(String wechat);

    List<User> list(String userIds);

    User loginAndBindParent(JSONObject userinfoJson);

    User byUserId(int parent);
}
