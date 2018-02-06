package game.domain.model.recharge;

import game.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;

/**
 * Created by pengyi
 * Date : 17-12-9.
 * desc:
 */
public class RechargeSelect extends ConcurrencySafeEntity {

    private int type;
    private BigDecimal price;
    private int currency;
    private int giveCurrency;
    private boolean agent;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getGiveCurrency() {
        return giveCurrency;
    }

    public void setGiveCurrency(int giveCurrency) {
        this.giveCurrency = giveCurrency;
    }

    public boolean isAgent() {
        return agent;
    }

    public void setAgent(boolean agent) {
        this.agent = agent;
    }
}
