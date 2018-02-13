package game.domain.service.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import game.application.commissiondetails.command.CreateCommand;
import game.core.enums.FlowType;
import game.core.pay.GameServer;
import game.domain.model.user.IUserParentRepository;
import game.domain.model.user.UserParent;
import game.domain.service.commissiondetailed.ICommissionDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
@Service("userParentService")
public class UserParentService implements IUserParentService {

    private final IUserParentRepository<UserParent, String> userParentRepository;
    private final GameServer gameServer;
    private final ICommissionDetailedService commissionDetailedService;

    @Autowired
    public UserParentService(IUserParentRepository<UserParent, String> userParentRepository, GameServer gameServer, ICommissionDetailedService commissionDetailedService) {
        this.userParentRepository = userParentRepository;
        this.gameServer = gameServer;
        this.commissionDetailedService = commissionDetailedService;
    }

    @Override
    public void bindParent(Integer userId, int parent) {
        UserParent userParent = userParentRepository.searchByUserId(userId);
        if (null == userParent) {
            UserParent myParent = userParentRepository.searchByUserId(parent);
            if (null != myParent) {
                userParent = new UserParent(userId, parent, myParent.getB(), myParent.getA(), 2);
            } else {
                myParent = new UserParent(parent, null, null, null, 2);
                userParentRepository.save(myParent);
                userParent = new UserParent(userId, parent, null, null, 2);
            }
            userParentRepository.save(userParent);
        }

        int parentSize = byParent(parent).size();
        if (5 == parentSize) {
            UserParent myParent = userParentRepository.searchByUserId(parent);
            myParent.setLevel(1);
            userParentRepository.save(myParent);
        }
    }

    @Override
    public int spreadCount(int id) {
        return userParentRepository.spreadCount(id);
    }

    @Override
    public void consumption(JSONArray jsonArray) {
        JSONArray notice = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Float card = jsonObject.getFloatValue("card");
            UserParent userParent = userParentRepository.searchByUserId(jsonObject.getIntValue("userId"));
            if (null != userParent) {
                Integer parent = userParent.getParent();
                userParent.setTotalRebate(userParent.getTotalRebate().add(BigDecimal.valueOf(card)));
                userParent.setTodayRebate(userParent.getTodayRebate().add(BigDecimal.valueOf(card)));
                userParentRepository.save(userParent);
                if (null != parent || 1 == userParent.getLevel()) {
                    CreateCommand createCommand = new CreateCommand();
                    UserParent userParent1;
                    if (1 == userParent.getLevel()) {
                        userParent1 = userParent;
                    } else {
                        userParent1 = userParentRepository.searchByUserId(parent);
                    }

                    if (null != userParent1.getLevel() && 1 == userParent1.getLevel()) {
                        createCommand.setFlowType(FlowType.IN_FLOW);
                        createCommand.setUserId(userParent1.getUserId());
                        createCommand.setMoney(BigDecimal.valueOf(0.38 * jsonObject.getFloatValue("card") / 110).setScale(2, RoundingMode.HALF_UP));
                        createCommand.setDescription(userParent.getUserId() + "消耗" + jsonObject.getFloatValue("card") + "房卡");
                        commissionDetailedService.create(createCommand);

                        userParent1.setTodaySelfRebate(userParent1.getTodaySelfRebate().add(createCommand.getMoney()));
//                        userParent1.setCommission(userParent1.getCommission().add(BigDecimal.valueOf(0.38 * jsonObject.getFloatValue("card") / 110).setScale(2, RoundingMode.HALF_UP)));
                    }
                    userParent1.setTodayRebate(userParent1.getTodayRebate().add(BigDecimal.valueOf(card)));
                    userParent1.setTotalRebate(userParent1.getTotalRebate().add(BigDecimal.valueOf(card)));
                    userParentRepository.save(userParent1);

                    parent = userParent1.getParent();
                    if (null != parent) {
                        UserParent userParent2 = userParentRepository.searchByUserId(parent);

                        if (null != userParent2.getLevel() && 1 == userParent2.getLevel()) {
                            createCommand = new CreateCommand();
                            createCommand.setFlowType(FlowType.IN_FLOW);
                            createCommand.setUserId(parent);
                            createCommand.setMoney(BigDecimal.valueOf(0.12 * jsonObject.getFloatValue("card") / 110).setScale(2, RoundingMode.HALF_UP));
                            createCommand.setDescription(userParent.getUserId() + "消耗" + jsonObject.getFloatValue("card") + "房卡");
                            commissionDetailedService.create(createCommand);

                            userParent2.setTodaySelfRebate(userParent2.getTodaySelfRebate().add(createCommand.getMoney()));
//                            userParent2.setCommission(userParent2.getCommission().add(BigDecimal.valueOf(0.12 * jsonObject.getFloatValue("card") / 110).setScale(2, RoundingMode.HALF_UP)));
                        }
                        userParent2.setTodayRebate(userParent2.getTodayRebate().add(BigDecimal.valueOf(card)));
                        userParent2.setTotalRebate(userParent2.getTotalRebate().add(BigDecimal.valueOf(card)));
                        userParentRepository.save(userParent2);

                        parent = userParent2.getParent();
                        if (null != parent) {
                            UserParent userParent3 = userParentRepository.searchByUserId(parent);
                            if (null != userParent3.getLevel() && 1 == userParent3.getLevel()) {
                                createCommand = new CreateCommand();
                                createCommand.setFlowType(FlowType.IN_FLOW);
                                createCommand.setUserId(parent);
                                createCommand.setMoney(BigDecimal.valueOf(0.06 * jsonObject.getFloatValue("card") / 110).setScale(2, RoundingMode.HALF_UP));
                                createCommand.setDescription(userParent.getUserId() + "消耗" + jsonObject.getFloatValue("card") + "房卡");
                                commissionDetailedService.create(createCommand);

                                userParent3.setTodaySelfRebate(userParent3.getTodaySelfRebate().add(createCommand.getMoney()));
//                                userParent3.setCommission(userParent3.getCommission().add(BigDecimal.valueOf(0.06 * jsonObject.getFloatValue("card") / 110).setScale(2, RoundingMode.HALF_UP)));
                            }
                            userParent3.setTodayRebate(userParent3.getTodayRebate().add(BigDecimal.valueOf(card)));
                            userParent3.setTotalRebate(userParent3.getTotalRebate().add(BigDecimal.valueOf(card)));
                            userParentRepository.save(userParent3);
                            parent = userParent1.getParent();

                            while (null != parent) {
                                UserParent userParent4 = userParentRepository.searchByUserId(parent);
                                if (null == userParent4) {
                                    return;
                                }
                                userParent4.setTodayRebate(userParent4.getTodayRebate().add(BigDecimal.valueOf(card)));
                                userParent4.setTotalRebate(userParent4.getTotalRebate().add(BigDecimal.valueOf(card)));
                                userParentRepository.save(userParent4);
                                parent = userParent4.getParent();
                            }
                        }
                    }
                }
            }
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
    public void lastDayRebateCommission(Integer userId) {
        UserParent userParent = byUserId(userId);
        BigDecimal rebate = BigDecimal.ZERO;
        if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(1000000))) {
            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.1)));
        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(500000))) {
            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.08)));
        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(200000))) {
            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.06)));
        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(100000))) {
            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.05)));
        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(50000))) {
            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.03)));
        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(20000))) {
            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.01)));
        }
        List<UserParent> userParents = byParent(userId);
        for (UserParent child : userParents) {
            if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(1000000))) {
                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.1)));
            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(500000))) {
                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.08)));
            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(200000))) {
                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.06)));
            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(100000))) {
                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.05)));
            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(50000))) {
                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.03)));
            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(20000))) {
                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.01)));
            }
        }
        userParent.setLastdayRebateCommission(rebate.setScale(2, RoundingMode.HALF_UP));
        userParent.setCommission(userParent.getCommission().add(rebate));
        userParent.setCommission(userParent.getCommission().add(userParent.getLastdaySelfRebate()));
        if (1 == userParent.getLevel()) {
            if (0 <= userParent.getLastdaySelfRebate().compareTo(BigDecimal.valueOf(500))) {
                userParent.setCommission(userParent.getCommission().add(BigDecimal.valueOf(50)));
                userParent.setLastdaySelfRebateCommission(BigDecimal.valueOf(50));
            }
            if (0 <= userParent.getLastdaySelfRebate().compareTo(BigDecimal.valueOf(1000))) {
                userParent.setCommission(userParent.getCommission().add(BigDecimal.valueOf(userParent.getLastdaySelfRebate().intValue() / 1000 * 100)));
                userParent.setLastdaySelfRebateCommission(BigDecimal.valueOf(userParent.getLastdaySelfRebate().intValue() / 1000 * 100));
            }
        }
        userParentRepository.save(userParent);
    }

}
