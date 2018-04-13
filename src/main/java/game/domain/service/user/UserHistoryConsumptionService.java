package game.domain.service.user;

import game.domain.model.user.IUserHistoryConsumptionRepository;
import game.domain.model.user.UserHistoryConsumption;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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
    public void add(int userId, BigDecimal rebate, Date date, BigDecimal consumption) {
        UserHistoryConsumption userConsumption = new UserHistoryConsumption(date, userId, rebate, consumption);
        userHistoryConsumptionRepository.save(userConsumption);
    }
}
