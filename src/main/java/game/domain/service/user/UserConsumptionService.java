package game.domain.service.user;

import game.domain.model.user.IUserConsumptionRepository;
import game.domain.model.user.UserConsumption;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
@Service("userConsumptionService")
public class UserConsumptionService implements IUserConsumptionService {

    private final IUserConsumptionRepository<UserConsumption, String> userConsumptionRepository;

    public UserConsumptionService(IUserConsumptionRepository<UserConsumption, String> userConsumptionRepository) {
        this.userConsumptionRepository = userConsumptionRepository;
    }

    @Override
    public void add(int userId, BigDecimal card) {
        UserConsumption userConsumption = new UserConsumption(userId, card);
        userConsumptionRepository.save(userConsumption);
    }
}
