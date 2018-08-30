package game.domain.service.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import game.application.commissiondetails.command.CreateCommand;
import game.core.enums.FlowType;
import game.core.pay.GameServer;
import game.domain.model.commissiondetailed.CommissionDetailed;
import game.domain.model.user.IUserParentRepository;
import game.domain.model.user.UserParent;
import game.domain.service.commissiondetailed.ICommissionDetailedService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
                userParent = new UserParent(userId, parent, myParent.getB(), myParent.getA(), 2, myParent.getGroupName());
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
        //讯米
//        double m1 = 0.4 * 0.9 / 108;
//        double m2 = 0.12 * 0.9 / 108;
//        double m3 = 0.08 * 0.9 / 108;
        //TODO
        //心悦
//        double m1 = 0.4 * 0.83 / 110;
//        double m2 = 0.12 * 0.83 / 110;
//        double m3 = 0.08 * 0.83 / 110;
        //江湖3
        double m1 = 0.4 * 0.83 * 0.98 / 100;
        double m2 = 0.12 * 0.83 * 0.98 / 100;
        double m3 = 0.08 * 0.83 * 0.98 / 100;
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
            userParentRepository.save(userParent);

            UserParent userParent1 = null;
            if (userParent.getLevel() == 1) {
                userParent1 = userParent;
            } else if (null == userParent.getParent()) {
                continue;
            } else {
                userParent1 = userParentRepository.searchByUserId(userParent.getParent());
            }
            if (null == userParent1 || userParent1.getLevel() != 1) {
                continue;
            }
            CreateCommand createCommand = new CreateCommand();
            createCommand.setFlowType(FlowType.IN_FLOW);
            createCommand.setUserId(userParent1.getUserId());
            createCommand.setMoney(BigDecimal.valueOf(m1).multiply(card).setScale(2, RoundingMode.HALF_UP));
            createCommand.setDescription(userParent.getUserId() + "消耗" + card.doubleValue());
            createCommand.setFromUser(userParent.getUserId());
            commissionDetailedService.create(createCommand);

            userParent1.setCommission(userParent1.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
            userParent1.setTodayCommission(userParent1.getTodayCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
            userParent1.setTotalCommission(userParent1.getTotalCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
            userParentRepository.save(userParent1);

            Integer parent = userParent1.getParent();
            if (null != parent) {
                UserParent userParent2 = userParentRepository.searchByUserId(parent);

                createCommand = new CreateCommand();
                createCommand.setFlowType(FlowType.IN_FLOW);
                createCommand.setUserId(parent);
                createCommand.setMoney(BigDecimal.valueOf(m2).multiply(card).setScale(2, RoundingMode.HALF_UP));
                createCommand.setDescription(userParent.getUserId() + "消耗" + card.doubleValue());
                createCommand.setFromUser(userParent.getUserId());
                commissionDetailedService.create(createCommand);

                userParent2.setCommission(userParent2.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                userParent2.setTodayCommission(userParent2.getTodayCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                userParent2.setTotalCommission(userParent2.getTotalCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                userParentRepository.save(userParent2);

                parent = userParent2.getParent();
                if (null != parent) {
                    UserParent userParent3 = userParentRepository.searchByUserId(parent);
                    createCommand = new CreateCommand();
                    createCommand.setFlowType(FlowType.IN_FLOW);
                    createCommand.setUserId(parent);
                    createCommand.setMoney(BigDecimal.valueOf(m3).multiply(card).setScale(2, RoundingMode.HALF_UP));
                    createCommand.setDescription(userParent.getUserId() + "消耗" + card.doubleValue());
                    createCommand.setFromUser(userParent.getUserId());
                    commissionDetailedService.create(createCommand);

                    userParent3.setCommission(userParent3.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                    userParent3.setTodayCommission(userParent3.getTodayCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                    userParent3.setTotalCommission(userParent3.getTotalCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                    userParentRepository.save(userParent3);
                }
            }
        }
    }

    @Override
    public UserParent byUserId(Integer userId) {
        return userParentRepository.searchByUserId(userId);
    }

    @Override
    public void lastDayRebate() {
        userParentRepository.updateLastDayRebate();
    }

    @Override
    public List<UserParent> allUserParent() {
        return userParentRepository.list(null, null);
    }

    @Override
    public List<Integer> daqu() {
        return userParentRepository.daqu();
    }

    @Override
    public void addAllDaquCommission(Map<String, BigDecimal> updateCommands) {
        for (Map.Entry<String, BigDecimal> entry : updateCommands.entrySet()) {
            userParentRepository.addDaquCommission(entry.getKey(), entry.getValue());
            logger.warn(entry.getKey() + "返利" + entry.getValue().setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        userParentRepository.flush();
        logger.warn("每日大区返利保存成功");
    }

    @Override
    public void ssssstt() {
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.like("description", "%大区%", MatchMode.ANYWHERE));
        List<CommissionDetailed> detaileds = commissionDetailedService.list(criterionList);
        Map<Integer, BigDecimal> userMons = new HashMap<>();
        List<Integer> users = new ArrayList<>();
        for (CommissionDetailed commissionDetailed : detaileds) {
            if (!userMons.containsKey(commissionDetailed.getUser())) {
                users.add(commissionDetailed.getUser());
                userMons.put(commissionDetailed.getUser(), BigDecimal.ZERO);
            }
            userMons.put(commissionDetailed.getUser(), userMons.get(commissionDetailed.getUser()).add(commissionDetailed.getMoney()));
        }

        criterionList.clear();
        criterionList.add(Restrictions.in("userId", users));
        List<UserParent> userParents = userParentRepository.list(criterionList, null);
        for (UserParent userParent1 : userParents) {
            if (userMons.get(userParent1.getUserId()).subtract(userParent1.getDaquTotalCommission()).compareTo(BigDecimal.ZERO) != 0) {
                logger.warn("刷新返利" + userParent1.getUserId());
                userParent1.setDaquCommission(userParent1.getDaquCommission().add(userMons.get(userParent1.getUserId())).subtract(userParent1.getDaquTotalCommission()));
                userParent1.setDaquTotalCommission(userMons.get(userParent1.getUserId()));
                userParentRepository.save(userParent1);
            }
        }
    }

}
