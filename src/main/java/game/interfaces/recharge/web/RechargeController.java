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
import game.core.exception.ApiPayException;
import game.core.exception.NoLoginException;
import game.core.pay.wechat.*;
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

//                String s = CoreHttpUtils.urlConnection("http://api.chishuicg.com/v1/main/create-order", "order_number=" + recharge.getRechargeNo() + "&order_total=" + recharge.getMoney().setScale(2, RoundingMode.HALF_UP).toString()
//                        + "&notify_url=" + Constants.ALIPAY_NOTIFY_URL + "&body=" + recharge.getRechargeNo() + "amount" + recharge.getMoney() + "&test=11");
//                JSONObject jsonObject = JSON.parseObject(s);
//                if (200 == jsonObject.getIntValue("code")) {
//                    jsonMessage.setMessage("成功");
//                    jsonMessage.setCode(0);
//                    jsonMessage.setData("alipay_sdk=alipay-sdk-php-20161101&app_id=2017122501209793&biz_content=%7B%22body%22%3A%22%E8%80%81%E7%8E%8B%E5%95%86%E5%9F%8E%22%2C%22subject%22%3A+%2218118909821830190%22%2C%22out_trade_no%22%3A+%2218118909821830190%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%2226%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F1t9439473s.iok.la%3A10338%2Fv1%2Fnotify%2Fdirect-pay&sign_type=RSA2&timestamp=2018-01-18+23%3A56%3A22&version=1.0&sign=eKmn4By5f2Z9ikFeN9iW0bp6LR7fakSlq8VLwLGxn7TK83%2BMXax6iOaPoa5TxSGgwxpT%2BImMSVgzOCFZ0A1cZCHexnZiA2bLy1QYGSLbfFJ0usoHhQyxuhskBdVJzJFni1WStZXmbOBsA7joLZAJc9gScmA%2BXUKUNN0dEV71xSFtsdhDAoTV6EmzDvqQWGmjqUtamxpNxdMYxMy3nBC3j4qf%2B%2BCyv1kEFeJZ9buWdvLsz6qruVj7oVyDH%2B%2Bw%2B%2BLhuv2%2BQGM7l4zjADU7mlBkCOmfBpcRwVttOMXPkaWWesZYU2EOm1qCRLtekzpem70MzWz72yliUCwdU1o5%2F448AQ%3D%3D");
//                } else {
//                    jsonMessage.setMessage("失败");
//                    jsonMessage.setCode(1);
//                }

//                //实例化客户端
//                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", Constants.ALIPAY_UNIFIED_URL, Constants.ALIPAY_APP_ID, "json", "UTF-8", Constants.ALIPAY_PUBLIC_KEY, "RSA2");
//                //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//                AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
//                //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//                AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//                model.setBody("我是测试数据");
//                model.setSubject("App支付测试Java");
//                model.setOutTradeNo("test12132165465416354");
//                model.setTimeoutExpress("30m");
//                model.setTotalAmount("0.01");
//                model.setProductCode("QUICK_MSECURITY_PAY");
//                alipayTradeAppPayRequest.setBizModel(model);
//                alipayTradeAppPayRequest.setNotifyUrl(Constants.ALIPAY_NOTIFY_URL);
//                try {
//                    //这里和普通的接口调用不同，使用的是sdkExecute
//                    AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayTradeAppPayRequest);
//                    System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
//                    jsonMessage.setMessage("成功");
//                    jsonMessage.setCode(0);
//                    jsonMessage.setData(response.getBody());
//                } catch (AlipayApiException e) {
//                    e.printStackTrace();
//                }


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
}
