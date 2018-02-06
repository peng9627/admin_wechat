package game.domain.service.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import game.application.commissiondetails.command.CreateCommand;
import game.core.enums.FlowType;
import game.core.pay.GameServer;
import game.core.util.CoreHttpUtils;
import game.core.util.CoreStringUtils;
import game.domain.model.user.IUserParentRepository;
import game.domain.model.user.UserParent;
import game.domain.service.commissiondetailed.ICommissionDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
                userParent = new UserParent(userId, parent, myParent.getB(), myParent.getA(), 3);
            } else {
                userParent = new UserParent(userId, parent, null, null, 3);
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
        JSONArray notice = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            UserParent userParent = userParentRepository.searchByUserId(jsonObject.getIntValue("userId"));
            if (null != userParent) {
                Integer a = userParent.getA();
                Integer b = userParent.getB();
                Integer parent = userParent.getParent();
                if (null != parent) {
                    UserParent parentUser = userParentRepository.searchByUserId(parent);
                    if (null == parentUser) {
                        UserParent myParent = new UserParent(parent, null, null, null, 3);
                        userParentRepository.save(myParent);
                        parentUser = myParent;
                    }
                    CreateCommand createCommand = new CreateCommand();
                    createCommand.setFlowType(FlowType.IN_FLOW);
                    createCommand.setUserId(parent);
                    createCommand.setMoney(BigDecimal.valueOf(0.32 / 108 * 0.95 * jsonObject.getFloatValue("card")).setScale(2, RoundingMode.HALF_UP));
                    createCommand.setDescription(userParent.getUserId() + "消耗" + jsonObject.getFloatValue("card") + "房卡");
                    commissionDetailedService.create(createCommand);
                    parentUser.setCommission(parentUser.getCommission().add(BigDecimal.valueOf(0.32 / 108 * 0.95 * jsonObject.getFloatValue("card"))).setScale(2, RoundingMode.HALF_UP));
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("playerId", parent);
                    jsonObject1.put("commission", parentUser.getCommission());
                    jsonObject1.put("extensionCount", userParentRepository.spreadCount(parent));
                    notice.add(jsonObject1);
                    userParentRepository.save(parentUser);
                    if (null != b && b != parent.intValue()) {
                        UserParent bUser = userParentRepository.searchByUserId(b);
                        createCommand.setFlowType(FlowType.IN_FLOW);
                        createCommand.setUserId(b);
                        createCommand.setMoney(BigDecimal.valueOf(0.12 / 108 * 0.95 * jsonObject.getFloatValue("card")).setScale(2, RoundingMode.HALF_UP));
                        createCommand.setDescription(userParent.getUserId() + "消耗" + jsonObject.getFloatValue("card") + "房卡");
                        commissionDetailedService.create(createCommand);
                        bUser.setCommission(bUser.getCommission().add(BigDecimal.valueOf(0.12 / 108 * 0.95 * jsonObject.getFloatValue("card"))).setScale(2, RoundingMode.HALF_UP));
                        JSONObject jsonObjectB = new JSONObject();
                        jsonObjectB.put("playerId", b);
                        jsonObjectB.put("commission", bUser.getCommission());
                        jsonObjectB.put("extensionCount", userParentRepository.spreadCount(b));
                        notice.add(jsonObjectB);
                        userParentRepository.save(bUser);
                        if (null != a && a != parent.intValue()) {
                            UserParent aUser = userParentRepository.searchByUserId(a);
                            createCommand.setFlowType(FlowType.IN_FLOW);
                            createCommand.setUserId(a);
                            createCommand.setMoney(BigDecimal.valueOf(0.06 / 108 * 0.95 * jsonObject.getFloatValue("card")));
                            createCommand.setDescription(userParent.getUserId() + "消耗" + jsonObject.getFloatValue("card") + "房卡");
                            commissionDetailedService.create(createCommand);
                            aUser.setCommission(aUser.getCommission().add(BigDecimal.valueOf(0.06 / 108 * 0.95 * jsonObject.getFloatValue("card"))).setScale(2, RoundingMode.HALF_UP));
                            JSONObject jsonObjectA = new JSONObject();
                            jsonObjectA.put("playerId", a);
                            jsonObjectA.put("commission", aUser.getCommission());
                            jsonObjectA.put("extensionCount", userParentRepository.spreadCount(a));
                            notice.add(jsonObjectA);
                            userParentRepository.save(aUser);
                        }
                    } else if (null != b && b == parent.intValue() && null != a) {
                        UserParent aUser = userParentRepository.searchByUserId(a);
                        createCommand.setFlowType(FlowType.IN_FLOW);
                        createCommand.setUserId(a);
                        createCommand.setMoney(BigDecimal.valueOf(0.12 / 108 * 0.95 * jsonObject.getFloatValue("card")).setScale(2, RoundingMode.HALF_UP));
                        createCommand.setDescription(userParent.getUserId() + "消耗" + jsonObject.getFloatValue("card") + "房卡");
                        commissionDetailedService.create(createCommand);
                        aUser.setCommission(aUser.getCommission().add(BigDecimal.valueOf(0.12 / 108 * 0.95 * jsonObject.getFloatValue("card"))).setScale(2, RoundingMode.HALF_UP));
                        JSONObject jsonObjectA = new JSONObject();
                        jsonObjectA.put("playerId", a);
                        jsonObjectA.put("commission", aUser.getCommission());
                        jsonObjectA.put("extensionCount", userParentRepository.spreadCount(a));
                        notice.add(jsonObjectA);
                        userParentRepository.save(aUser);
                    }
                }
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (0 != notice.size()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("list", notice);
                    jsonObject.put("enc", CoreStringUtils.md5(notice.size() + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
                    String s = CoreHttpUtils.urlConnection(gameServer.getUrl(), "extension_commission=" + jsonObject.toJSONString());
                    if (!CoreStringUtils.isEmpty(s)) {
                        JSONObject result = JSONObject.parseObject(s);
                    }
                }
            }
        }).start();
    }

    @Override
    public UserParent byUserId(Integer userId) {
        return userParentRepository.searchByUserId(userId);
    }

}
