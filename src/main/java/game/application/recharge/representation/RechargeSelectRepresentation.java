package game.application.recharge.representation;


import java.math.BigDecimal;

/**
 * Created by pengyi
 * Date : 16-7-19.
 */
public class RechargeSelectRepresentation {

    private String id;

    private int type;
    private BigDecimal price;
    private int currency;
    private int giveCurrency;
    private boolean agent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
