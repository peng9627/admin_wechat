package game.domain.service.user;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
public interface IUserHistoryConsumptionService {
    void add(int userId, BigDecimal card, Date date);
}
