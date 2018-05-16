package game.domain.service.user;

import com.alibaba.fastjson.JSONArray;
import game.domain.model.user.UserParent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
public interface IUserParentService {
    void bindParent(Integer userId, int parent);

    int spreadCount(int id);

    void consumption(JSONArray jsonArray);

    UserParent byUserId(Integer userId);

    List<UserParent> byParent(Integer parent);

    void lastDayRebate();

    List<Integer> userIds();

    List<Integer> daqu();

    List<UserParent> allUserParent();

    void addCommission(Integer parentId, BigDecimal commission);

    void addAllCommission(Map<String, BigDecimal> updateCommands);
}
