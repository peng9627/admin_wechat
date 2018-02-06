package game.interfaces.wechat.api;

import com.alibaba.fastjson.JSONObject;
import game.application.user.IUserAppService;
import game.application.user.representation.UserRepresentation;
import game.application.wechat.TextMessage;
import game.application.wechat.command.WechatCommand;
import game.application.wechat.command.WechatMessage;
import game.core.pay.wechat.XMLParser;
import game.core.util.BeanToXml;
import game.core.util.CoreStringUtils;
import game.core.util.WXBizMsgCrypt;
import game.core.wechat.WechatApi;
import game.interfaces.shared.api.BaseApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by pengyi
 * Date : 17-12-22.
 * desc:
 */
@Controller
@RequestMapping("/api/wechat")
public class ApiWechatNotice extends BaseApiController {


    private final IUserAppService userAppService;

    @Autowired
    public ApiWechatNotice(IUserAppService userAppService) {
        this.userAppService = userAppService;
    }

    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public void notice(String echostr, HttpServletResponse response) {
        try {
            if (!CoreStringUtils.isEmpty(echostr)) {
                response.getWriter().print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    public void notice(HttpServletRequest request, HttpServletResponse response) {

        int contentLength = request.getContentLength();
        byte[] bytes = new byte[contentLength];
        try {
            request.getInputStream().read(bytes, 0, contentLength);
            logger.info("response---------------->" + new String(bytes, "utf-8"));
            WechatMessage wechatMessage = (WechatMessage) XMLParser.getObjFromXML(new String(bytes, "utf-8"), WechatMessage.class);
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt("J1CmeOVjP1APK8hKyEMgSy8UCUAFU3H8CfN98krthtv");
            String xml = wxBizMsgCrypt.decrypt(wechatMessage.getEncrypt());
            WechatCommand wechatCommand = (WechatCommand) XMLParser.getObjFromXML(xml, WechatCommand.class);
            logger.info("createTime:" + wechatCommand.getCreateTime());
            logger.info("event:" + wechatCommand.getEvent());
            logger.info("messageType:" + wechatCommand.getMsgType());
            logger.info("fromuser:" + wechatCommand.getFromUserName());
            logger.info("toUser:" + wechatCommand.getToUserName());
            switch (wechatCommand.getMsgType()) {
                case "event":
                    switch (wechatCommand.getEvent()) {
                        case "subscribe":
                            UserRepresentation userRepresentation;
                            JSONObject userinfoJson = WechatApi.getUserInfo(wechatCommand.getFromUserName());
                            String gameId = "";
                            if (!CoreStringUtils.isEmpty("EventKey")) {
                                logger.info("EventKey:" + wechatCommand.getEventKey());
                                gameId = wechatCommand.getEventKey().replace("qrscene_", "");
                                userinfoJson.put("parent", Integer.parseInt(gameId));
                            }
                            userRepresentation = userAppService.loginAndBindParent(userinfoJson);
                            TextMessage textMessage = new TextMessage();
                            textMessage.setToUserName(wechatCommand.getFromUserName());
                            textMessage.setFromUserName(wechatCommand.getToUserName());
                            textMessage.setCreateTime(new Date().getTime() + "");
                            textMessage.setMsgType("text");
                            textMessage.setFuncFlag(0);
                            textMessage.setContent(userRepresentation.getUserId() + "" + gameId);
                            response.getWriter().write(BeanToXml.beanToXml(textMessage, TextMessage.class));
                            break;
                        case "unsubscribe":

                            break;
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
