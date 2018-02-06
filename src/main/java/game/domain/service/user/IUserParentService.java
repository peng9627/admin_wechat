package game.domain.service.user;

import com.alibaba.fastjson.JSONArray;
import game.domain.model.user.UserParent;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
public interface IUserParentService {
    void bindParent(Integer userId, int parent);

    int spreadCount(int id);

    void consumption(JSONArray jsonArray);

    UserParent byUserId(Integer userId);
}
