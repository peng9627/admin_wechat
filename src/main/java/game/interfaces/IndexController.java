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
            //江湖麻将馆
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx988152b62477873e&redirect_uri=http%3a%2f%2fzf.zzjhmjg.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //大众互娱
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbb4693c82ec025a5&redirect_uri=http%3a%2f%2fdazhonghuyuzf.chuangmikeji.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //山城
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2d4606a7e7af7ba9&redirect_uri=http%3a%2f%2fshanchenghuyuzf.chuangmikeji.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //西北
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx24242b2bf731f7d7&redirect_uri=http%3a%2f%2fzf.zhuanxinyu.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //牡丹江
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx15468dd1bb6be97f&redirect_uri=http%3a%2f%2fzf.mdjpoker.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //江城互娱
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5f92b5138fa881da&redirect_uri=http%3a%2f%2fpay.jingsaibang.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //盛天互娱
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8f6bc16c163a4f91&redirect_uri=http%3a%2f%2fzf.zmbaobei.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //全民互娱
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WECHAT_OFFICE_APPID + "&redirect_uri=http%3a%2f%2fquanminzf.chuangmikeji.com%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
            //九州
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WECHAT_OFFICE_APPID + "&redirect_uri=http%3a%2f%2fzf.hi528.cn%2fuser%2flogin_wechat&response_type=code&scope=snsapi_userinfo&state=" + id + "#wechat_redirect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
