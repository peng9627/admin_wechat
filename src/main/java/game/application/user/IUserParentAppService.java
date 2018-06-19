package game.application.user;

import com.alibaba.fastjson.JSONArray;
import game.application.user.representation.UserParentRepresentation;
import game.domain.model.user.UserParent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
public interface IUserParentAppService {
    int spreadCount(int userId);

    void consumption(JSONArray jsonArray);

    UserParentRepresentation byUserId(Integer userId);

    void lastDayRebate();

    List<UserParent> allUserParent();

    List<Integer> daqu();

    void addAllDaquCommission(Map<String, BigDecimal> updateCommands);
}
