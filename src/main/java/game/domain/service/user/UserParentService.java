package game.domain.service.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import game.application.commissiondetails.command.CreateCommand;
import game.core.enums.FlowType;
import game.core.pay.GameServer;
import game.core.util.CoreDateUtils;
import game.core.util.CoreHttpUtils;
import game.core.util.CoreStringUtils;
import game.domain.model.user.IUserParentRepository;
import game.domain.model.user.UserParent;
import game.domain.service.commissiondetailed.ICommissionDetailedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
@Service("userParentService")
public class UserParentService implements IUserParentService {

    private final IUserParentRepository<UserParent, String> userParentRepository;
    private final GameServer gameServer;
    private final ICommissionDetailedService commissionDetailedService;
    private final IUserConsumptionService userConsumptionService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserParentService(IUserParentRepository<UserParent, String> userParentRepository, GameServer gameServer, ICommissionDetailedService commissionDetailedService, IUserConsumptionService userConsumptionService) {
        this.userParentRepository = userParentRepository;
        this.gameServer = gameServer;
        this.commissionDetailedService = commissionDetailedService;
        this.userConsumptionService = userConsumptionService;
    }

    @Override
    public void bindParent(Integer userId, int parent) {
        UserParent userParent = userParentRepository.searchByUserId(userId);
        if (null == userParent) {
            UserParent myParent = userParentRepository.searchByUserId(parent);
            if (null != myParent) {
                userParent = new UserParent(userId, parent, myParent.getB(), myParent.getA(), 1, myParent.getGroupName());
                Map<String, Object> map = new HashMap<>();
                String str = 1 + "&_&" + userId + "&_&" + 20 + "&_&" + gameServer.getKey();
                String enc = CoreStringUtils.md5(str, 32, false, "utf-8");
                map.put("manager", 1);
                map.put("target", userId);
                map.put("permission", 20);
                map.put("enc", enc);
                String result = null;
                try {
                    String s = CoreHttpUtils.urlConnection(gameServer.getUrl(), "update_permission=" + JSON.toJSONString(map));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userParentRepository.save(userParent);
            }
        }

    }

    @Override
    public int spreadCount(int id) {
        return userParentRepository.spreadCount(id);
    }

    @Override
    public void consumption(JSONArray jsonArray) {
        JSONArray notice = new JSONArray();
        double m1 = 0.4 / 108;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            BigDecimal card = BigDecimal.valueOf(BigDecimal.valueOf(jsonObject.getFloatValue("card")).setScale(2, RoundingMode.HALF_UP).doubleValue());
            UserParent userParent = userParentRepository.searchByUserId(jsonObject.getIntValue("userId"));
            userConsumptionService.add(jsonObject.getIntValue("userId"), card);
            if (null == userParent) {
                continue;
            }
            userParent.setTotalConsumption(userParent.getTotalConsumption().add(card));
            userParent.setTodayConsumption(userParent.getTodayConsumption().add(card));

            CreateCommand createCommand = new CreateCommand();
            createCommand.setFlowType(FlowType.IN_FLOW);
            createCommand.setUserId(userParent.getUserId());
            createCommand.setMoney(BigDecimal.valueOf(m1).multiply(card).setScale(2, RoundingMode.HALF_UP));
            createCommand.setDescription(userParent.getUserId() + "消耗" + card.doubleValue() + "房卡");
            createCommand.setFromUser(userParent.getUserId());
            commissionDetailedService.create(createCommand);

            userParent.setCommission(userParent.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
            userParent.setTodayCommission(userParent.getTodayCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
            userParent.setTotalCommission(userParent.getTotalCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
            userParentRepository.save(userParent);
        }
    }

    @Override
    public UserParent byUserId(Integer userId) {
        return userParentRepository.searchByUserId(userId);
    }

    @Override
    public List<UserParent> byParent(Integer parent) {
        return userParentRepository.byParent(parent);
    }

    @Override
    public void lastDayRebate() {
        userParentRepository.updateLastDayRebate();
    }

    @Override
    public List<Integer> userIds() {
        return userParentRepository.userIds();
    }

    @Override
    public List<Integer> daqu() {
        return userParentRepository.daqu();
    }

    @Override
    public List<UserParent> allUserParent() {
        return userParentRepository.list(null, null);
    }

    @Override
    public void addCommission(Integer parentId, BigDecimal commission) {
        UserParent userParent = byUserId(parentId);
        userParent.setCommission(userParent.getCommission().add(commission));
        userParent.setLastDayCommission(userParent.getLastDayCommission().add(commission));
        userParent.setTotalCommission(userParent.getTotalCommission().add(commission));
        userParentRepository.save(userParent);
    }

    @Override
    public void addAllCommission(Map<String, BigDecimal> updateCommands) {
        for (Map.Entry<String, BigDecimal> entry : updateCommands.entrySet()) {
            userParentRepository.addCommission(entry.getKey(), entry.getValue());
        }
    }

}
