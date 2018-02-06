package game.application.user;

import com.alibaba.fastjson.JSONObject;
import game.application.user.representation.UserRepresentation;
import game.core.mapping.IMappingService;
import game.domain.model.user.User;
import game.domain.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
@Service("userAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserAppService implements IUserAppService {

    private final IUserService userService;

    private final IMappingService mappingService;

    @Autowired
    public UserAppService(IUserService userService, IMappingService mappingService) {
        this.userService = userService;
        this.mappingService = mappingService;
    }

    @Override
    public UserRepresentation loginAndBindParent(JSONObject userinfoJson) {
        return mappingService.map(userService.loginAndBindParent(userinfoJson), UserRepresentation.class, false);
    }

    @Override
    public UserRepresentation byUserId(int parent) {
        User user = userService.byUserId(parent);
        if (null != user) {
            return mappingService.map(user, UserRepresentation.class, false);
        }
        return null;
    }

}
