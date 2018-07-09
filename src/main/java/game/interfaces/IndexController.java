package game.interfaces;

import game.application.user.IUserAppService;
import game.core.common.Constants;
import game.interfaces.shared.api.BaseApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Author pengyi
 * Date 17-5-25.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseApiController {

    private final IUserAppService userAppService;

    @Autowired
    public IndexController(IUserAppService userAppService) {
        this.userAppService = userAppService;
    }

    @RequestMapping(value = "/share/{id}")
    public void index(@PathVariable String id, HttpServletResponse response) {
        try {
            //TODO
            //江湖2
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WECHAT_OFFICE_APPID + "&redirect_uri=http%3a%2f%2fjmzf.zzjhmjg.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //xinyue
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WECHAT_OFFICE_APPID + "&redirect_uri=http%3a%2f%2fxinyuezf.dcpnm.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
