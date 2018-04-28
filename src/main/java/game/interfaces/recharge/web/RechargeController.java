package game.interfaces.recharge.web;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import game.application.recharge.IRechargeAppService;
import game.application.recharge.command.CreateRechargeCommand;
import game.application.recharge.command.ListRechargeCommand;
import game.core.common.Constants;
import game.core.enums.PayType;
import game.core.enums.YesOrNoStatus;
import game.core.exception.ApiPayException;
import game.core.exception.NoLoginException;
import game.core.pay.ChengfutongRecharge;
import game.core.pay.wechat.*;
import game.core.util.CoreDateUtils;
import game.core.util.CoreHttpUtils;
import game.domain.model.recharge.Recharge;
import game.interfaces.shared.web.BaseController;
import game.interfaces.shared.web.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by pengyi
 * Date : 16-7-19.
 */
@Controller
@RequestMapping("/recharge")
public class RechargeController extends BaseController {

    private final IRechargeAppService rechargeAppService;

    @Autowired
    public RechargeController(IRechargeAppService rechargeAppService) {
        this.rechargeAppService = rechargeAppService;
    }

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListRechargeCommand command) {
        return new ModelAndView("/recharge/list", "pagination", rechargeAppService.pagination(command))
                .addObject("command", command).addObject("totalMoney", rechargeAppService.totalMoney(command));
    }

    @RequestMapping(value = "/recharge")
    @ResponseBody
    public JsonMessage recharge(CreateRechargeCommand command, HttpServletRequest request) {

        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setPayType(command.getPayType().name());
        if (null == command.getId() || 0 == command.getUserId()) {
            jsonMessage.setCode(1);
            jsonMessage.setMessage("id不能为空");
            return jsonMessage;
        }
        try {
            String ip = CoreHttpUtils.getClientIP(request);
            command.setIp(ip);
            Recharge recharge = rechargeAppService.recharge(command);

            if (null != command.getPayType() && command.getPayType() == PayType.ALIPAY) {
                //实例化客户端
                AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIPAY_UNIFIED_URL, Constants.ALIPAY_APP_ID,
                        Constants.ALIPAY_APP_PRIVATE_KEY, "json", "UTF-8", Constants.ALIPAY_PUBLIC_KEY, "RSA2");
                //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
                AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
                //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
                AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                model.setBody("" + recharge.getRechargeNo() + "amount" + recharge.getMoney());
                model.setSubject("" + recharge.getRechargeNo());
                model.setOutTradeNo(recharge.getRechargeNo());
                model.setTimeoutExpress("30m");
                model.setTotalAmount(recharge.getMoney().setScale(2, RoundingMode.HALF_UP).toString());
                model.setProductCode("QUICK_MSECURITY_PAY");
                alipayTradeAppPayRequest.setBizModel(model);
                alipayTradeAppPayRequest.setNotifyUrl(Constants.ALIPAY_NOTIFY_URL);
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayTradeAppPayRequest);
                jsonMessage.setMessage("成功");
                jsonMessage.setCode(0);
                jsonMessage.setData(response.getBody());
                return jsonMessage;
            } else {
                String body = "订单号：" + recharge.getRechargeNo();
                String detail = "描述";
                UnifiedRequest unifiedRequest = new UnifiedRequest(body, detail, recharge.getRechargeNo(), recharge.getMoney().multiply(new BigDecimal(100)).intValue(), ip, Constants.WECHAT_NOTIFY_URL);
                UnifiedResponse unifiedResponse = null;
                WechatPayHandle wechatPayHandle = new WechatPayHandle();
                String sign = Signature.getSign(unifiedRequest.toMap());
                unifiedRequest.setSign(sign);
                XStream xStream = new XStream(new DomDriver("utf-8", new XmlFriendlyNameCoder("-_", "_")));
                xStream.alias("xml", UnifiedRequest.class);
                String s = CoreHttpUtils.wechatUnified(Constants.WECHAT_UNIFIED_URL, xStream.toXML(unifiedRequest));
                unifiedResponse = (UnifiedResponse) XMLParser.getObjFromXML(s, UnifiedResponse.class);
                if (unifiedResponse != null) {
                    logger.warn("微信统一下单返回错误码" + unifiedResponse.getResult_code() + "--->" + unifiedResponse.getReturn_msg());
                    unifiedResponse.setTime_stamp(System.currentTimeMillis() / 1000);
                    unifiedResponse.setNonce_str(RandomStringGenerator.getRandomStringByLength(16));
                    Map<String, Object> map = new HashMap<>();
                    map.put("appid", unifiedResponse.getAppid());
                    map.put("package", "Sign=WXPay");
                    map.put("partnerid", unifiedResponse.getMch_id());
                    map.put("prepayid", unifiedResponse.getPrepay_id());
                    map.put("noncestr", unifiedResponse.getNonce_str());
                    map.put("timestamp", unifiedResponse.getTime_stamp());
                    unifiedResponse.setSign(Signature.getSign(map));
                    System.out.println(unifiedResponse.getSign());

                    wechatPayHandle.setAppId(unifiedResponse.getAppid());
                    wechatPayHandle.setTimeStamp(unifiedResponse.getTime_stamp() + "");
                    wechatPayHandle.setNonceStr(unifiedResponse.getNonce_str());
                    wechatPayHandle.setPackages("Sign=WXPay");
                    wechatPayHandle.setPrepayId(unifiedResponse.getPrepay_id());
                    wechatPayHandle.setPartnerId(unifiedResponse.getMch_id());
                    wechatPayHandle.setSign(unifiedResponse.getSign());

                    jsonMessage.setMessage("成功");
                    jsonMessage.setCode(0);
                    jsonMessage.setData(URLEncoder.encode(JSON.toJSONString(wechatPayHandle), "utf-8"));
                    return jsonMessage;
                }
                jsonMessage.setCode(1);
                jsonMessage.setMessage("下单失败");
                return jsonMessage;
            }
        } catch (ApiPayException e) {
            logger.warn(e.getMessage());
            jsonMessage.setCode(2);
            jsonMessage.setMessage(e.getMessage());
            return jsonMessage;
        } catch (NoLoginException e) {
            logger.warn(e.getMessage());
            jsonMessage.setCode(1);
            jsonMessage.setMessage(e.getMessage());
            return jsonMessage;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            jsonMessage.setCode(1);
            jsonMessage.setMessage("下单失败");
            return jsonMessage;
        }
    }

    @RequestMapping(value = "/chengfutong_wechat_recharge")
    @ResponseBody
    public ModelAndView chengfutongWechatRecharge(CreateRechargeCommand command, HttpServletRequest request) {

        if (null == command.getId() || 0 == command.getUserId()) {
            return new ModelAndView("/fail", "message", "支付失败");
        }
        try {
            String ip = CoreHttpUtils.getClientIP(request);
            command.setIp(ip);
            command.setPayType(PayType.WECHAT);
//            Recharge recharge = rechargeAppService.recharge(command);
            Recharge recharge = new Recharge("zf00" + new Random().nextInt(10000), command.getUserId(), BigDecimal.valueOf(10), YesOrNoStatus.NO, PayType.WECHAT, command.getId());
            ChengfutongRecharge chengfutongRecharge = new ChengfutongRecharge();
            chengfutongRecharge.setP1_yingyongnum(Constants.CHENGFUTONGID);
            chengfutongRecharge.setP2_ordernumber(recharge.getRechargeNo());
            chengfutongRecharge.setP3_money(recharge.getMoney().setScale(2, RoundingMode.UP).toString());
            chengfutongRecharge.setP6_ordertime(CoreDateUtils.formatDate(recharge.getCreateDate(), "yyyyMMddhhmmss"));
            chengfutongRecharge.setP7_productcode("ZFBZFWAP");
            chengfutongRecharge.setP14_customname(recharge.getUserId().toString());
            chengfutongRecharge.setP16_customip(ip.replace(".", "_"));
            chengfutongRecharge.setP25_terminal("1");
            String client = CoreHttpUtils.getLoginPlatform(request);
            if (null != client) {
                if (client.equals("Android")) {
                    chengfutongRecharge.setP25_terminal("3");
                } else if (client.equals("iPhone") || client.equals("iPad") || client.equals("Mac")) {
                    chengfutongRecharge.setP25_terminal("2");
                }
            }
            chengfutongRecharge.signset();
            if (null != command.getPayType()) {
                return new ModelAndView("/cftsubmit", "info", chengfutongRecharge);
            }
            return new ModelAndView("/fail", "message", "支付失败");
//        } catch (ApiPayException e) {
//            logger.warn(e.getMessage());
//            return new ModelAndView("/fail", "message", "超出限额");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ModelAndView("/fail", "message", "支付失败");
        }
    }

    @RequestMapping(value = "/chengfutong_qq_recharge")
    @ResponseBody
    public ModelAndView chengfutongQQRecharge(CreateRechargeCommand command, HttpServletRequest request) {

        if (null == command.getId() || 0 == command.getUserId()) {
            return new ModelAndView("/fail", "message", "支付失败");
        }
        try {
            String ip = CoreHttpUtils.getClientIP(request);
            command.setIp(ip);
            command.setPayType(PayType.ALL);
            Recharge recharge = rechargeAppService.recharge(command);

            ChengfutongRecharge chengfutongRecharge = new ChengfutongRecharge();
            chengfutongRecharge.setP1_yingyongnum(Constants.CHENGFUTONGID);
            chengfutongRecharge.setP2_ordernumber(recharge.getRechargeNo());
            chengfutongRecharge.setP3_money(recharge.getMoney().setScale(2, RoundingMode.UP).toString());
            chengfutongRecharge.setP6_ordertime(CoreDateUtils.formatDate(recharge.getCreateDate(), "yyyyMMddhhmmss"));
            chengfutongRecharge.setP7_productcode("QQWAP");
            chengfutongRecharge.setP14_customname(recharge.getUserId().toString());
            chengfutongRecharge.setP16_customip(ip.replace(".", "_"));
            chengfutongRecharge.setP25_terminal("1");
            String client = CoreHttpUtils.getLoginPlatform(request);
            if (null != client) {
                if (client.equals("Android")) {
                    chengfutongRecharge.setP25_terminal("3");
                } else if (client.equals("iPhone") || client.equals("iPad") || client.equals("Mac")) {
                    chengfutongRecharge.setP25_terminal("2");
                }
            }
            chengfutongRecharge.signset();
            if (null != command.getPayType() && command.getPayType() == PayType.WECHAT) {
                return new ModelAndView("/cftsubmit", "info", chengfutongRecharge);
            }
            return new ModelAndView("/fail", "message", "支付失败");
        } catch (ApiPayException e) {
            logger.warn(e.getMessage());
            return new ModelAndView("/fail", "message", "超出限额");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ModelAndView("/fail", "message", "支付失败");
        }
    }
}