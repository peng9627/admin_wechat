package game.domain.service.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import game.application.commissiondetails.command.CreateCommand;
import game.core.enums.FlowType;
import game.core.pay.GameServer;
import game.core.util.CoreDateUtils;
import game.domain.model.user.IUserParentRepository;
import game.domain.model.user.UserParent;
import game.domain.service.commissiondetailed.ICommissionDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
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
    private final IUserConsumptionService userConsumptionService;
    private final IUserHistoryConsumptionService userHistoryConsumptionService;

    @Autowired
    public UserParentService(IUserParentRepository<UserParent, String> userParentRepository, GameServer gameServer, ICommissionDetailedService commissionDetailedService, IUserConsumptionService userConsumptionService, IUserHistoryConsumptionService userHistoryConsumptionService) {
        this.userParentRepository = userParentRepository;
        this.gameServer = gameServer;
        this.commissionDetailedService = commissionDetailedService;
        this.userConsumptionService = userConsumptionService;
        this.userHistoryConsumptionService = userHistoryConsumptionService;
    }

    @Override
    public void bindParent(Integer userId, int parent) {
        UserParent userParent = userParentRepository.searchByUserId(userId);
        if (null == userParent) {
            UserParent myParent = userParentRepository.searchByUserId(parent);
            if (null != myParent) {
                userParent = new UserParent(userId, parent, myParent.getB(), myParent.getA(), 4);
            } else {
                myParent = new UserParent(parent, null, null, null, 4);
                userParentRepository.save(myParent);
                userParent = new UserParent(userId, parent, null, null, 4);
            }
            userParentRepository.save(userParent);
        }

    }

    @Override
    public int spreadCount(int id) {
        return userParentRepository.spreadCount(id);
    }

    @Override
    public void consumption(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            BigDecimal card = BigDecimal.valueOf(BigDecimal.valueOf(jsonObject.getFloatValue("card")).setScale(2, RoundingMode.HALF_UP).doubleValue());
            UserParent userParent = userParentRepository.searchByUserId(jsonObject.getIntValue("userId"));
            userConsumptionService.add(jsonObject.getIntValue("userId"), card.setScale(2, RoundingMode.HALF_UP));
            if (null == userParent) {
                userParent = new UserParent(jsonObject.getIntValue("userId"), null, null, null, 4);
            }
            userParent.setTotalConsumption(userParent.getTotalConsumption().add(card));
            userParent.setTodayConsumption(userParent.getTodayConsumption().add(card));

            userParent.setTotalRebate(userParent.getTotalRebate().add(card).setScale(2, RoundingMode.HALF_UP));
            userParent.setTodayRebate(userParent.getTodayRebate().add(card).setScale(2, RoundingMode.HALF_UP));
            userParentRepository.save(userParent);

            if (null != userParent.getParent()) {
                UserParent userParent1 = userParentRepository.searchByUserId(userParent.getParent());
                if (null != userParent1) {
                    switch (userParent1.getLevel()) {
                        case 3:
                            CreateCommand createCommand = new CreateCommand();
                            createCommand.setFlowType(FlowType.IN_FLOW);
                            createCommand.setUserId(userParent1.getUserId());
                            createCommand.setMoney(BigDecimal.valueOf(0.3).multiply(card).setScale(2, RoundingMode.HALF_UP));
                            createCommand.setDescription(userParent.getUserId() + "消耗" + card.doubleValue() + "房卡");
                            commissionDetailedService.create(createCommand);

                            userParent1.setTodaySelfRebate(userParent1.getTodaySelfRebate().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                            userParent1.setCommission(userParent1.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                            userParentRepository.save(userParent1);

                            UserParent userParent2 = userParentRepository.searchByUserId(userParent1.getParent());
                            if (null != userParent2) {
                                createCommand = new CreateCommand();
                                createCommand.setUserId(userParent2.getUserId());
                                createCommand.setMoney(BigDecimal.valueOf(0.2).multiply(card).setScale(2, RoundingMode.HALF_UP));
                                commissionDetailedService.create(createCommand);

                                userParent2.setTodaySelfRebate(userParent2.getTodaySelfRebate().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                                userParent2.setCommission(userParent2.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                                userParentRepository.save(userParent2);

                                UserParent userParent3 = userParentRepository.searchByUserId(userParent1.getParent());
                                if (null != userParent3) {
                                    createCommand = new CreateCommand();
                                    createCommand.setUserId(userParent3.getUserId());
                                    createCommand.setMoney(BigDecimal.valueOf(0.1).multiply(card).setScale(2, RoundingMode.HALF_UP));
                                    commissionDetailedService.create(createCommand);

                                    userParent3.setTodaySelfRebate(userParent3.getTodaySelfRebate().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                                    userParent3.setCommission(userParent3.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                                    userParentRepository.save(userParent3);
                                }
                            }
                            break;
                        case 2:
                            createCommand = new CreateCommand();
                            createCommand.setFlowType(FlowType.IN_FLOW);
                            createCommand.setUserId(userParent1.getUserId());
                            createCommand.setMoney(BigDecimal.valueOf(0.5).multiply(card).setScale(2, RoundingMode.HALF_UP));
                            createCommand.setDescription(userParent.getUserId() + "消耗" + card.doubleValue() + "房卡");
                            commissionDetailedService.create(createCommand);

                            userParent1.setTodaySelfRebate(userParent1.getTodaySelfRebate().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                            userParent1.setCommission(userParent1.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                            userParentRepository.save(userParent1);

                            userParent2 = userParentRepository.searchByUserId(userParent1.getParent());
                            if (null != userParent2) {
                                createCommand = new CreateCommand();
                                createCommand.setUserId(userParent2.getUserId());
                                createCommand.setMoney(BigDecimal.valueOf(0.1).multiply(card).setScale(2, RoundingMode.HALF_UP));
                                commissionDetailedService.create(createCommand);

                                userParent2.setTodaySelfRebate(userParent2.getTodaySelfRebate().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                                userParent2.setCommission(userParent2.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                                userParentRepository.save(userParent2);
                            }
                            break;
                        case 1:
                            createCommand = new CreateCommand();
                            createCommand.setFlowType(FlowType.IN_FLOW);
                            createCommand.setUserId(userParent1.getUserId());
                            createCommand.setMoney(BigDecimal.valueOf(0.6).multiply(card).setScale(2, RoundingMode.HALF_UP));
                            createCommand.setDescription(userParent.getUserId() + "消耗" + card.doubleValue() + "房卡");
                            commissionDetailedService.create(createCommand);

                            userParent1.setTodaySelfRebate(userParent1.getTodaySelfRebate().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                            userParent1.setCommission(userParent1.getCommission().add(createCommand.getMoney()).setScale(2, RoundingMode.HALF_UP));
                            userParentRepository.save(userParent1);
                            break;
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
        userHistoryConsumptionService.add(userId, userParent.getLastdayRebate(), CoreDateUtils.addDay(new Date(), -1), userParent.getLastDayConsumption());
//        BigDecimal rebate = BigDecimal.ZERO;
//        if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(1000000))) {
//            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.1)));
//        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(500000))) {
//            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.08)));
//        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(200000))) {
//            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.06)));
//        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(100000))) {
//            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.05)));
//        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(50000))) {
//            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.03)));
//        } else if (1 == userParent.getLevel() && 0 <= (userParent.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(20000))) {
//            rebate = rebate.add(userParent.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.01)));
//        }
//        List<UserParent> userParents = byParent(userId);
//        for (UserParent child : userParents) {
//            if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(1000000))) {
//                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.1)));
//            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(500000))) {
//                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.08)));
//            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(200000))) {
//                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.06)));
//            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(100000))) {
//                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.05)));
//            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(50000))) {
//                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.03)));
//            } else if (1 == child.getLevel() && 0 <= (child.getLastdayTotalRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP)).compareTo(BigDecimal.valueOf(20000))) {
//                rebate = rebate.subtract(child.getLastdayRebate().divide(BigDecimal.valueOf(110), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.01)));
//            }
//        }
//        userParent.setLastdayRebateCommission(rebate.setScale(2, RoundingMode.HALF_UP));
//        userParent.setCommission(userParent.getCommission().add(rebate));
//        userParent.setCommission(userParent.getCommission().add(userParent.getLastdaySelfRebate()));
//        if (1 == userParent.getLevel()) {
//            if (0 <= userParent.getLastdaySelfRebate().compareTo(BigDecimal.valueOf(500))) {
//                userParent.setCommission(userParent.getCommission().add(BigDecimal.valueOf(50)));
//                userParent.setLastdaySelfRebateCommission(BigDecimal.valueOf(50));
//            }
//            if (0 <= userParent.getLastdaySelfRebate().compareTo(BigDecimal.valueOf(1000))) {
//                userParent.setCommission(userParent.getCommission().add(BigDecimal.valueOf(userParent.getLastdaySelfRebate().intValue() / 1000 * 100)));
//                userParent.setLastdaySelfRebateCommission(BigDecimal.valueOf(userParent.getLastdaySelfRebate().intValue() / 1000 * 100));
//            }
//        }
//        userParentRepository.save(userParent);
    }

}
