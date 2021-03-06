package game.application.commissiondetails.command;


import game.core.enums.FlowType;
import game.core.enums.PayType;

import java.math.BigDecimal;

/**
 * Created by pengyi on 2016/4/11.
 */
public class CreateCommand {

    private int userId;
    private BigDecimal money;
    private FlowType flowType;  //资金流向类型
    private String description;     //说明

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

