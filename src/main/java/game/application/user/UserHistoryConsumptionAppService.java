package game.application.user;

import game.domain.model.user.UserHistoryConsumption;
import game.domain.service.user.IUserHistoryConsumptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengyi
 * Date : 18-5-14.
 * desc:
 */
@Service("userHistoryConsumptionAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserHistoryConsumptionAppService implements IUserHistoryConsumptionAppService {

    private final IUserHistoryConsumptionService userHistoryConsumptionService;

    public UserHistoryConsumptionAppService(IUserHistoryConsumptionService userHistoryConsumptionService) {
        this.userHistoryConsumptionService = userHistoryConsumptionService;
    }

    @Override
    public void createAll(List<UserHistoryConsumption> userHistoryConsumptions) {
        userHistoryConsumptionService.createAll(userHistoryConsumptions);
    }
}
