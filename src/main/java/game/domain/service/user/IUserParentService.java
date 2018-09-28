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

    void lastDayRebate();

    List<UserParent> allUserParent();

    List<Integer> daqu();

    void addAllDaquCommission(Map<String, BigDecimal> updateCommands);

    void ssssstt();

    void externalConsumption(JSONArray jsonArray);
}
