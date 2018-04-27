package game.domain.service.recharge;

import com.alibaba.fastjson.JSONObject;
import game.application.recharge.command.CreateRechargeCommand;
import game.application.recharge.command.ListRechargeCommand;
import game.core.common.id.IdFactory;
import game.core.enums.YesOrNoStatus;
import game.core.exception.ApiPayException;
import game.core.exception.NoFoundException;
import game.core.pay.ChengfutongNotice;
import game.core.pay.GameServer;
import game.core.pay.wechat.WechatNotify;
import game.core.util.CoreDateUtils;
import game.core.util.CoreHttpUtils;
import game.core.util.CoreStringUtils;
import game.domain.model.recharge.IRechargeRepository;
import game.domain.model.recharge.Recharge;
import game.domain.model.recharge.RechargeSelect;
import game.domain.model.user.UserParent;
import game.domain.service.user.IUserParentService;
import game.domain.service.user.IUserService;
import game.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by pengyi
 * Date : 16-7-9.
 */
@Service("rechargeService")
public class RechargeService implements IRechargeService {

    private final IRechargeRepository<Recharge, String> rechargeRepository;

    private final IUserService userService;

    private final IdFactory idFactory;
    private final GameServer gameServer;
    private final IRechargeSelectService rechargeSelectService;
    private final IUserParentService userParentService;

    @Autowired
    public RechargeService(IRechargeRepository<Recharge, String> rechargeRepository, IUserService userService, IdFactory idFactory, GameServer gameServer, IRechargeSelectService rechargeSelectService, IUserParentService userParentService) {
        this.rechargeRepository = rechargeRepository;
        this.userService = userService;
        this.idFactory = idFactory;
        this.gameServer = gameServer;
        this.rechargeSelectService = rechargeSelectService;
        this.userParentService = userParentService;
    }

    @Override
    public Pagination<Recharge> pagination(ListRechargeCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("user.userName", command.getUserName(), MatchMode.ANYWHERE));
            aliasMap.put("user", "user");
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")));
        }
        if (null != command.getIsSuccess() && command.getIsSuccess() != YesOrNoStatus.ALL) {
            criterionList.add(Restrictions.eq("isSuccess", command.getIsSuccess()));
        } else {
            criterionList.add(Restrictions.or(Restrictions.eq("isSuccess", YesOrNoStatus.YES), Restrictions.and(Restrictions.eq("isSuccess", YesOrNoStatus.NO), Restrictions.ge("createDate", CoreDateUtils.addDay(new Date(), -3)))));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return rechargeRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }

    @Override
    public List<Recharge> list(ListRechargeCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.eq("user.userName", command.getUserName()));
            aliasMap.put("user", "user");
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return rechargeRepository.list(criterionList, orderList, null, null, aliasMap, 100);
    }

    @Override
    public Recharge pay(CreateRechargeCommand command) {

        String rechargeNo;
        while (true) {
            rechargeNo = idFactory.getNextId();
            Recharge recharge = rechargeRepository.byRechargeNo(rechargeNo);
            if (null == recharge) {
                break;
            }
        }
        Recharge recharge = new Recharge(rechargeNo, command.getUserId(), command.getMoney(), YesOrNoStatus.NO, command.getPayType(), command.getId());

        rechargeRepository.save(recharge);
        return recharge;
    }

    @Override
    public Recharge getById(String agent_bill_id) {
        return rechargeRepository.getById(agent_bill_id);
    }

    @Override
    public BigDecimal totalMoney(ListRechargeCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("user.userName", command.getUserName(), MatchMode.ANYWHERE));
            aliasMap.put("user", "user");
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")));
        }
        if (null != command.getIsSuccess() && command.getIsSuccess() != YesOrNoStatus.ALL) {
            criterionList.add(Restrictions.eq("isSuccess", command.getIsSuccess()));
        }
        return rechargeRepository.total(criterionList, aliasMap);
    }

    @Override
    public BigDecimal totalMoneyEq(ListRechargeCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.eq("user.userName", command.getUserName()));
            aliasMap.put("user", "user");
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")));
        }
        if (null != command.getIsSuccess() && command.getIsSuccess() != YesOrNoStatus.ALL) {
            criterionList.add(Restrictions.eq("isSuccess", command.getIsSuccess()));
        }
        return rechargeRepository.total(criterionList, aliasMap);
    }

    @Override
    public Pagination<Recharge> paginationEq(ListRechargeCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.eq("user.userName", command.getUserName()));
            aliasMap.put("user", "user");
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")));
        }
        if (null != command.getIsSuccess() && command.getIsSuccess() != YesOrNoStatus.ALL) {
            criterionList.add(Restrictions.eq("isSuccess", command.getIsSuccess()));
        } else {
            criterionList.add(Restrictions.or(Restrictions.eq("isSuccess", YesOrNoStatus.YES), Restrictions.and(Restrictions.eq("isSuccess", YesOrNoStatus.NO), Restrictions.ge("createDate", CoreDateUtils.addDay(new Date(), -3)))));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return rechargeRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }

    @Override
    public Recharge recharge(CreateRechargeCommand command) throws ApiPayException {
        RechargeSelect rechargeSelect = rechargeSelectService.getById(command.getId());
        if (null == rechargeSelect) {
            throw new NoFoundException("id为" + command.getId() + "的记录不存在");
        }
        BigDecimal todayTotal = rechargeRepository.todayTotal(command.getUserId());
        //九州
//        if (null != todayTotal && 0 < todayTotal.add(rechargeSelect.getPrice()).compareTo(BigDecimal.valueOf(2000))) {
//            throw new ApiPayException("超出限额");
//        }
        //讯米
        if (null != todayTotal && 0 < todayTotal.add(rechargeSelect.getPrice()).compareTo(BigDecimal.valueOf(1000))) {
            throw new ApiPayException("超出限额");
        }
        String no = idFactory.getNextId();
        Recharge recharge = new Recharge(no, command.getUserId(), rechargeSelect.getPrice(), YesOrNoStatus.NO, command.getPayType(), command.getId());
        rechargeRepository.save(recharge);
        return recharge;
    }

    @Override
    public void apiWechatSuccess(WechatNotify notify) {
        Recharge recharge = this.searchByNo(notify.getOut_trade_no());
        if (null != recharge && null == recharge.getPayTime() && 0 != recharge.getIsSuccess().compareTo(YesOrNoStatus.YES)) {
            recharge.changePayTime(CoreDateUtils.parseDate(notify.getTime_end(), "yyyyMMddHHmmss"));
            recharge.changePayNo(notify.getTransaction_id());
            recharge.changeIsSuccess(YesOrNoStatus.YES);
            rechargeRepository.update(recharge);

            RechargeSelect rechargeSelect = rechargeSelectService.getById(recharge.getSelectId());
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("manager", 998);
                jsonObject.put("target", recharge.getUserId());
                if (1 == rechargeSelect.getType()) {
                    jsonObject.put("card", rechargeSelect.getCurrency());
                    jsonObject.put("enc", CoreStringUtils.md5(998 + "&_&" + 0 + "&_&" + recharge.getUserId() + "&_&" + rechargeSelect.getCurrency() + "&_&" + 0 + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
                } else {
                    jsonObject.put("gold", rechargeSelect.getCurrency());
                    jsonObject.put("enc", CoreStringUtils.md5(998 + "&_&" + 0 + "&_&" + recharge.getUserId() + "&_&" + 0 + "&_&" + rechargeSelect.getCurrency() + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
                }
                String s = CoreHttpUtils.urlConnection(gameServer.getUrl(), "add_card=" + jsonObject.toJSONString());
                if (!CoreStringUtils.isEmpty(s)) {
                    JSONObject result = JSONObject.parseObject(s);
                    if (0 == result.getIntValue("error_code")) {
                        recharge.setNotifyTime(new Date());
                        rechargeRepository.update(recharge);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Recharge searchByNo(String rechargeNo) {
        return rechargeRepository.searchByNo(rechargeNo);
    }

    @Override
    public void apiAlipaySuccess(Map<String, String> params) {
        Recharge recharge = this.searchByNo(params.get("out_trade_no"));
        if (null != recharge && null == recharge.getPayTime() && 0 != recharge.getIsSuccess().compareTo(YesOrNoStatus.YES)) {
            recharge.changePayTime(CoreDateUtils.parseDate(params.get("gmt_payment"), "yyyy-MM-dd HH:mm:ss"));
            recharge.changePayNo(params.get("trade_no"));
            recharge.changeIsSuccess(YesOrNoStatus.YES);
            rechargeRepository.update(recharge);

            RechargeSelect rechargeSelect = rechargeSelectService.getById(recharge.getSelectId());
            int currency = rechargeSelect.getCurrency();
//            UserParent userParent = userParentService.byUserId(recharge.getUserId());
//            if (null != userParent && null != userParent.getLevel() && 3 == userParent.getLevel()) {
//                currency += rechargeSelect.getGiveCurrency();
//            }
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("manager", 998);
                jsonObject.put("target", recharge.getUserId());
                if (1 == rechargeSelect.getType()) {
                    jsonObject.put("card", currency);
                    jsonObject.put("enc", CoreStringUtils.md5(998 + "&_&" + 0 + "&_&" + recharge.getUserId() + "&_&" + currency + "&_&" + 0 + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
                } else {
                    jsonObject.put("gold", currency);
                    jsonObject.put("enc", CoreStringUtils.md5(998 + "&_&" + 0 + "&_&" + recharge.getUserId() + "&_&" + 0 + "&_&" + currency + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
                }
                String s = CoreHttpUtils.urlConnection(gameServer.getUrl(), "add_card=" + jsonObject.toJSONString());
                if (!CoreStringUtils.isEmpty(s)) {
                    JSONObject result = JSONObject.parseObject(s);
                    if (0 == result.getIntValue("error_code")) {
                        recharge.setNotifyTime(new Date());
                        rechargeRepository.update(recharge);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiChengfutongSuccess(ChengfutongNotice notice) {
        Recharge recharge = this.searchByNo(notice.getP2_ordernumber());
        if (null != recharge && null == recharge.getPayTime() && 0 != recharge.getIsSuccess().compareTo(YesOrNoStatus.YES)) {
            recharge.changePayTime(new Date());
            recharge.changePayNo(notice.getP5_orderid());
            recharge.changeIsSuccess(YesOrNoStatus.YES);
            rechargeRepository.update(recharge);

            RechargeSelect rechargeSelect = rechargeSelectService.getById(recharge.getSelectId());
            int currency = rechargeSelect.getCurrency();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("manager", 998);
                jsonObject.put("target", recharge.getUserId());
                if (1 == rechargeSelect.getType()) {
                    jsonObject.put("card", currency);
                    jsonObject.put("enc", CoreStringUtils.md5(998 + "&_&" + 0 + "&_&" + recharge.getUserId() + "&_&" + currency + "&_&" + 0 + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
                } else {
                    jsonObject.put("gold", currency);
                    jsonObject.put("enc", CoreStringUtils.md5(998 + "&_&" + 0 + "&_&" + recharge.getUserId() + "&_&" + 0 + "&_&" + currency + "&_&" + gameServer.getKey(), 32, false, "utf-8"));
                }
                String s = CoreHttpUtils.urlConnection(gameServer.getUrl(), "add_card=" + jsonObject.toJSONString());
                if (!CoreStringUtils.isEmpty(s)) {
                    JSONObject result = JSONObject.parseObject(s);
                    if (0 == result.getIntValue("error_code")) {
                        recharge.setNotifyTime(new Date());
                        rechargeRepository.update(recharge);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
