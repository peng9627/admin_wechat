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
    private Integer area;
    private BigDecimal commission;      //佣金
    private BigDecimal totalCommission; //总佣金
    private BigDecimal todayCommission;//今日佣金
    private BigDecimal lastDayCommission;//昨日佣金
    private BigDecimal totalConsumption;//总消费
    private BigDecimal todayConsumption;//今日消费
    private BigDecimal lastDayConsumption;//昨日消费
    private Date bindDate;              //绑定时间
    private String groupName;           //团队,名称

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

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getArea() {
        return area;
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

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    public BigDecimal getTodayCommission() {
        return todayCommission;
    }

    public void setTodayCommission(BigDecimal todayCommission) {
        this.todayCommission = todayCommission;
    }

    public BigDecimal getLastDayCommission() {
        return lastDayCommission;
    }

    public void setLastDayCommission(BigDecimal lastDayCommission) {
        this.lastDayCommission = lastDayCommission;
    }

    public BigDecimal getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(BigDecimal totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public BigDecimal getTodayConsumption() {
        return todayConsumption;
    }

    public void setTodayConsumption(BigDecimal todayConsumption) {
        this.todayConsumption = todayConsumption;
    }

    public BigDecimal getLastDayConsumption() {
        return lastDayConsumption;
    }

    public void setLastDayConsumption(BigDecimal lastDayConsumption) {
        this.lastDayConsumption = lastDayConsumption;
    }

    public Date getBindDate() {
        return bindDate;
    }

    public void setBindDate(Date bindDate) {
        this.bindDate = bindDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public UserParent() {
    }

    public UserParent(Integer userId, Integer parent, Integer b, Integer a, Integer level, String groupName) {
        this.userId = userId;
        this.parent = parent;
        this.b = b;
        this.a = a;
        this.level = level;
        this.commission = BigDecimal.ZERO;
        this.totalCommission = BigDecimal.ZERO;
        this.todayCommission = BigDecimal.ZERO;
        this.lastDayCommission = BigDecimal.ZERO;
        this.totalConsumption = BigDecimal.ZERO;
        this.todayConsumption = BigDecimal.ZERO;
        this.lastDayConsumption = BigDecimal.ZERO;
        this.groupName = groupName;
        setCreateDate(new Date());
        this.bindDate = new Date();
    }
}
