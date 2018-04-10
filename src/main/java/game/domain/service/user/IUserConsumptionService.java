package game.domain.service.user;

import java.math.BigDecimal;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
public interface IUserConsumptionService {
    void add(int userId, BigDecimal card);
}
