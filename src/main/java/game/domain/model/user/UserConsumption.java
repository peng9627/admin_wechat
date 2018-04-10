package game.domain.model.user;

import game.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by pengyi
 * Date : 18-3-15.
 * desc:
 */
public class UserConsumption extends ConcurrencySafeEntity {

    private Integer userId;
    private BigDecimal consumption;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public UserConsumption() {
    }

    public UserConsumption(Integer userId, BigDecimal consumption) {
        this.userId = userId;
        this.consumption = consumption;
        setCreateDate(new Date());
    }
}
