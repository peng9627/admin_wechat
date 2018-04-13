package game.domain.model.user;

import game.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by pengyi
 * Date : 18-4-10.
 * desc:
 */
public class UserHistoryConsumption extends ConcurrencySafeEntity {

    private Date date;

    private Integer userId;

    private BigDecimal rebate;

    private BigDecimal consumption;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public UserHistoryConsumption() {
    }

    public UserHistoryConsumption(Date date, Integer userId, BigDecimal rebate, BigDecimal consumption) {
        this.date = date;
        this.userId = userId;
        this.rebate = rebate;
        this.consumption = consumption;
    }
}
