package game.domain.service.user;

import game.domain.model.user.IUserExternalConsumptionRepository;
import game.domain.model.user.UserExternalConsumption;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
@Service("userExternalConsumptionService")
public class UserExternalConsumptionService implements IUserExternalConsumptionService {

    private final IUserExternalConsumptionRepository<UserExternalConsumption, String> userExternalConsumptionRepository;

    public UserExternalConsumptionService(IUserExternalConsumptionRepository<UserExternalConsumption, String> userExternalConsumptionRepository) {
        this.userExternalConsumptionRepository = userExternalConsumptionRepository;
    }

    @Override
    public void add(int userId, BigDecimal card) {
        UserExternalConsumption userExternalConsumption = new UserExternalConsumption(userId, card);
        userExternalConsumptionRepository.save(userExternalConsumption);
    }
}
