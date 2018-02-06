package game.application.user;

import com.alibaba.fastjson.JSONArray;
import game.application.user.representation.UserParentRepresentation;
import game.core.mapping.IMappingService;
import game.domain.model.user.UserParent;
import game.domain.service.user.IUserParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
@Service("userParentAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserParentAppService implements IUserParentAppService {

    private final IUserParentService userParentService;

    private final IMappingService mappingService;

    @Autowired
    public UserParentAppService(IUserParentService userParentService, IMappingService mappingService) {
        this.userParentService = userParentService;
        this.mappingService = mappingService;
    }

    @Override
    public int spreadCount(int userId) {
        return userParentService.spreadCount(userId);
    }

    @Override
    public void consumption(JSONArray jsonArray) {
        userParentService.consumption(jsonArray);
    }

    @Override
    public UserParentRepresentation byUserId(Integer userId) {
        UserParent userParent = userParentService.byUserId(userId);
        if (null == userParent) {
            return null;
        }
        return mappingService.map(userParentService.byUserId(userId), UserParentRepresentation.class, false);
    }
}
