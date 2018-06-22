package game.interfaces.recharge.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import game.application.recharge.IRechargeAppService;
import game.application.recharge.command.ListRechargeCommand;
import game.application.recharge.representation.ApiRechargeRepresentation;
import game.core.api.ApiResponse;
import game.core.api.ApiReturnCode;
import game.core.common.Constants;
import game.core.enums.PayType;
import game.core.exception.ApiAuthenticationException;
import game.core.pay.ChengfutongNotice;
import game.core.pay.wechat.Signature;
import game.core.pay.wechat.WechatNotify;
import game.core.pay.wechat.XMLParser;
import game.interfaces.shared.api.BaseApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/recharge")
public class ApiRechargeController extends BaseApiController {

    private final IRechargeAppService rechargeAppService;

    @Autowired
    public ApiRechargeController(IRechargeAppService rechargeAppService) {
        this.rechargeAppService = rechargeAppService;
    }

    @RequestMapping(value = "/list")
    public void pagination(HttpServletRequest request, HttpServletResponse response) {

        ApiResponse<List> apiResponse;
        try {
            ListRechargeCommand command = this.authenticationAndConvert(request, ListRechargeCommand.class);
            List<ApiRechargeRepresentation> pagination = rechargeAppService.list(command);
            apiResponse = new ApiResponse<>(ApiReturnCode.SUCCESS, pagination);

        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse<>(ApiReturnCode.AUTHENTICATION_FAILURE);
        } catch (Exception e) {
            apiResponse = new ApiResponse<>(ApiReturnCode.ERROR_UNKNOWN);
        }
        this.returnData(response, apiResponse);
    }

    @RequestMapping(value = "/wechat/notify")
    public void rechargeWecatNotify(HttpServletRequest request, HttpServletResponse response) {
        int contentLength = request.getContentLength();
        byte[] bytes = new byte[contentLength];
        WechatNotify notify = null;
        try {
            request.getInputStream().read(bytes, 0, contentLength);
            notify = (WechatNotify) XMLParser.getObjFromXML(new String(bytes), WechatNotify.class);
            logger.info("response---------------->" + new String(bytes, "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sign = null;
        if (notify != null) {
            sign = notify.getSign();
            notify.setSign("");
            try {
                String mySign = Signature.getWechatSign(notify);
                if (mySign.equals(sign)) {
                    if (notify.getReturn_code().equals("SUCCESS")) {
                        if (notify.getResult_code().equals("SUCCESS")) {
                            rechargeAppService.wechatSuccess(notify);
                            logger.info("充值流水号为[" + notify.getOut_trade_no() + "]的订单支付成功，支付方式为[" + PayType.WECHAT + "]");
                            response.getWriter().write("<xml>\n" +
                                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                                    "</xml>");
                        } else {
                            logger.info("充值流水号为[" + notify.getOut_trade_no() + "]的订单支付失败，原因[" + notify.getErr_code_des() + "]");
                        }

                    } else {
                        logger.info("充值流水号为[" + notify.getOut_trade_no() + "]的订单支付失败，原因[" + notify.getReturn_msg() + "]");
                    }
                } else {
                    logger.info("充值流水号为[" + notify.getOut_trade_no() + "]的订单支付失败，原因[{1}]");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @RequestMapping(value = "/alipay/notify")
    public void rechargeAlipayNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, Constants.ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
            if (flag) {
                if (params.get("trade_status").equals("TRADE_SUCCESS")) {
                    rechargeAppService.alipaySuccess(params);
                    logger.info("充值流水号为[" + params.get("out_trade_no") + "]的订单支付成功，支付方式为[" + PayType.ALIPAY + "]");
                    response.getWriter().write("ok");
                }
            } else {
                logger.warn("支付宝验签失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/chengfutong/notify")
    public void rechargeChengfutongNotify(ChengfutongNotice notice, HttpServletResponse response) {
        try {
            if (notice.signset()) {
                if (notice.getP4_zfstate().equals("1")) {
                    rechargeAppService.chengfutongSuccess(notice);
                    logger.info("充值流水号为[" + notice.getP2_ordernumber() + "]的订单支付成功，支付方式为[" + PayType.ALL + "]");
                    response.getWriter().write("success");
                }
            } else {
                logger.warn("验签失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/chengfutong_zz/notify")
    public void rechargeChengfutongZZNotify(ChengfutongNotice notice, HttpServletResponse response) {
        try {
            if (notice.signZZset()) {
                if (notice.getP4_zfstate().equals("1")) {
                    rechargeAppService.chengfutongSuccess(notice);
                    logger.info("充值流水号为[" + notice.getP2_ordernumber() + "]的订单支付成功，支付方式为[" + PayType.ALL + "]");
                    response.getWriter().write("success");
                }
            } else {
                logger.warn("验签失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
