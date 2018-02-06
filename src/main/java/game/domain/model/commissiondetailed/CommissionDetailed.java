package game.domain.model.commissiondetailed;

import game.core.enums.FlowType;
import game.core.id.ConcurrencySafeEntity;
import game.domain.model.user.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by pengyi on 2016/3/9.
 */
public class CommissionDetailed extends ConcurrencySafeEntity {

    private Integer user;                  //用户
    private FlowType flowType;          //资金流向类型
    private BigDecimal money;                  //金额
    private String description;         //说明（）

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommissionDetailed() {
        super();
    }

    public CommissionDetailed(Integer user, FlowType flowType, BigDecimal money, String description) {
        super();
        this.user = user;
        this.flowType = flowType;
        this.money = money;
        this.description = description;
        setCreateDate(new Date());
    }
}
