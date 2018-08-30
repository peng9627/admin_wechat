package game.domain.service.user;

import com.alibaba.fastjson.JSONObject;
import game.core.pay.GameServer;
import game.core.util.CoreHttpUtils;
import game.core.util.CoreStringUtils;
import game.domain.model.user.IUserRepository;
import game.domain.model.user.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
@Service("userService")
public class UserService implements IUserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final IUserRepository<User, String> userRepository;
    private final GameServer gameServer;
    private final IUserParentService userParentService;

    @Autowired
    public UserService(IUserRepository<User, String> userRepository, GameServer gameServer, IUserParentService userParentService) {
        this.userRepository = userRepository;
        this.gameServer = gameServer;
        this.userParentService = userParentService;
    }

    @Override
    public User searchByUserId(int userId) {
        return userRepository.searchByUserId(userId);
    }

    @Override
    public User searchByWechat(String wechat) {
        return userRepository.searchByWechat(wechat);
    }

    @Override
    public List<User> list(String userIds) {
        List<Criterion> criterionList = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        if (!CoreStringUtils.isEmpty(userIds)) {
            String[] ids = userIds.split(",");
            for (String userId : ids) {
                integers.add(Integer.valueOf(userId));
            }
            criterionList.add(Restrictions.in("userId", integers));
        }
        return userRepository.list(criterionList, null);
    }

    @Override
    public User loginAndBindParent(JSONObject userinfoJson) {

        User user;
        if (userinfoJson.containsKey(userinfoJson.getString("unionid"))) {
            user = userRepository.searchByWechat(userinfoJson.getString("unionid"));
        } else {
            user = userRepository.searchByUserId(userinfoJson.getIntValue("userId"));
        }
        int playerId = 0;
        if (null == user && userinfoJson.containsKey("unionid")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", userinfoJson.getString("unionid"));
            jsonObject.put("password", CoreStringUtils.md5(userinfoJson.getString("unionid"), 32, false, "utf-8"));
            jsonObject.put("headUrl", userinfoJson.getString("headimgurl"));
            jsonObject.put("nick", userinfoJson.getString("nickname"));
            jsonObject.put("sex", userinfoJson.getIntValue("sex"));
            jsonObject.put("enc", CoreStringUtils.md5(userinfoJson.getString("unionid") + "&_&" + CoreStringUtils.md5(userinfoJson.getString("unionid"), 32, false, "utf-8") + "&_&" + userinfoJson.getString("headimgurl") + "&_&" + userinfoJson.getString("nickname") + "&_&" + userinfoJson.getIntValue("sex") + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
            String s = CoreHttpUtils.urlConnection(gameServer.getUrl(), "create_account=" + jsonObject.toJSONString());
            logger.info("创建帐号返回" + s);
            if (!CoreStringUtils.isEmpty(s)) {
                JSONObject result = JSONObject.parseObject(s);
                if (0 == result.getIntValue("error_code")) {
                    JSONObject data = result.getJSONObject("data");
                    playerId = data.getIntValue("playerId");
                    user = userRepository.searchByUserId(playerId);
                }
            }
        }
        if (null == user && 0 == playerId) {
            return null;
        }
        if (null == user) {
            logger.info("新账号绑定前");
            if (userinfoJson.containsKey("parent") && 0 != userinfoJson.getIntValue("parent") && playerId != userinfoJson.getIntValue("parent")) {
                userParentService.bindParent(playerId, userinfoJson.getIntValue("parent"));
            }
            user = userRepository.searchByUserId(playerId);
        } else {
            logger.info("旧账号绑定前");
            if (userinfoJson.containsKey("parent") && 0 != userinfoJson.getIntValue("parent") && user.getUserId() != userinfoJson.getIntValue("parent")) {
                userParentService.bindParent(user.getUserId(), userinfoJson.getIntValue("parent"));
            }
        }
        logger.info("绑定后");
        return user;
    }

    @Override
    public User byUserId(int parent) {
        return userRepository.searchByUserId(parent);
    }
}
