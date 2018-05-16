package game.domain.service.user;

import game.domain.model.user.IUserHistoryConsumptionRepository;
import game.domain.model.user.UserHistoryConsumption;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
@Service("userHistoryConsumptionService")
public class UserHistoryConsumptionService implements IUserHistoryConsumptionService {

    private final IUserHistoryConsumptionRepository<UserHistoryConsumption, String> userHistoryConsumptionRepository;

    public UserHistoryConsumptionService(IUserHistoryConsumptionRepository<UserHistoryConsumption, String> userHistoryConsumptionRepository) {
        this.userHistoryConsumptionRepository = userHistoryConsumptionRepository;
    }

    @Override
    public void createAll(List<UserHistoryConsumption> userHistoryConsumptions) {
        for (UserHistoryConsumption userHistoryConsumption : userHistoryConsumptions) {
            userHistoryConsumptionRepository.save(userHistoryConsumption);
        }
    }
}
