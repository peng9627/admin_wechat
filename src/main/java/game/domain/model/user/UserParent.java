package game.domain.model.user;

import game.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户
 * Created by pengyi on 2016/4/15.
 */
public class UserParent extends ConcurrencySafeEntity {

    private Integer userId;             //用户id
    private Integer parent;             //上级
    private Integer b;                  //b级代理
    private Integer a;                  //a级代理
    private Integer level;              //等级
    private BigDecimal commission;      //佣金
    private BigDecimal todayRebate;     //今日佣金
    private BigDecimal totalRebate;     //总佣金
    private BigDecimal lastdayRebateCommission;//昨日佣金返利(获得的)
    private BigDecimal lastdayRebate;     //昨日佣金
    private BigDecimal lastdayTotalRebate;//昨日总佣金
    private BigDecimal todaySelfRebate;     //今日自己佣金
    private BigDecimal lastdaySelfRebate;     //昨日自己佣金
    private BigDecimal lastdaySelfRebateCommission;//昨日自己佣金返利(获得的)

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getTodayRebate() {
        return todayRebate;
    }

    public void setTodayRebate(BigDecimal todayRebate) {
        this.todayRebate = todayRebate;
    }

    public BigDecimal getTotalRebate() {
        return totalRebate;
    }

    public void setTotalRebate(BigDecimal totalRebate) {
        this.totalRebate = totalRebate;
    }

    public BigDecimal getLastdayRebateCommission() {
        return lastdayRebateCommission;
    }

    public void setLastdayRebateCommission(BigDecimal lastdayRebateCommission) {
        this.lastdayRebateCommission = lastdayRebateCommission;
    }

    public BigDecimal getLastdayRebate() {
        return lastdayRebate;
    }

    public void setLastdayRebate(BigDecimal lastdayRebate) {
        this.lastdayRebate = lastdayRebate;
    }

    public BigDecimal getLastdayTotalRebate() {
        return lastdayTotalRebate;
    }

    public void setLastdayTotalRebate(BigDecimal lastdayTotalRebate) {
        this.lastdayTotalRebate = lastdayTotalRebate;
    }

    public BigDecimal getTodaySelfRebate() {
        return todaySelfRebate;
    }

    public void setTodaySelfRebate(BigDecimal todaySelfRebate) {
        this.todaySelfRebate = todaySelfRebate;
    }

    public BigDecimal getLastdaySelfRebate() {
        return lastdaySelfRebate;
    }

    public void setLastdaySelfRebate(BigDecimal lastdaySelfRebate) {
        this.lastdaySelfRebate = lastdaySelfRebate;
    }

    public BigDecimal getLastdaySelfRebateCommission() {
        return lastdaySelfRebateCommission;
    }

    public void setLastdaySelfRebateCommission(BigDecimal lastdaySelfRebateCommission) {
        this.lastdaySelfRebateCommission = lastdaySelfRebateCommission;
    }

    public UserParent() {
    }

    public UserParent(Integer userId, Integer parent, Integer b, Integer a, Integer level) {
        this.userId = userId;
        this.parent = parent;
        this.b = b;
        this.a = a;
        this.level = level;
        this.commission = BigDecimal.ZERO;
        this.todayRebate = BigDecimal.ZERO;
        this.totalRebate = BigDecimal.ZERO;
        this.lastdayRebate = BigDecimal.ZERO;
        this.lastdayTotalRebate = BigDecimal.ZERO;
        this.lastdayRebateCommission = BigDecimal.ZERO;
        this.todaySelfRebate = BigDecimal.ZERO;
        this.lastdaySelfRebate = BigDecimal.ZERO;
        this.lastdaySelfRebateCommission = BigDecimal.ZERO;
        setCreateDate(new Date());
    }
}
