package game.core.common;

/**
 * Created by pengyi on 2016/4/10 0010.
 */
public class Constants {

    //只用于支付  万州
//    public static String WECHAT_APPID = "wx2ad1695bd3590d13";                       //appid
//    public static String WECHAT_KEY = "DFU0gmW1O0rKSzXuZtCCaHWbS95QuhRe";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://zf.zzjhmjg.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1497279342";
//    public static String ALIPAY_APP_ID = "2018011601903194";
//    public static String ALIPAY_APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC+fNIzaEGiMId6ATDGiYeGQPS7/jlZOIQPZtSDaVI2rMezJCzc6LIikTuVXyJ7KdcIJHrLD9gURauuNMWZN7f6U1MXyyutKLw59frtRXB16ioWooCPWzkgalPxNF+OJ291I+ggueWDF6Jrv6FHCEL4dR7PrEyZ6c5qcb5Hwxf2Y3DCijKNQYOXCL37cmKBm6UOZfGgSMwbVomaE1OamQ0Y6npd9T1nHcQbgfW0dpS8YH0TEDh7yAdaFyAKh2YiKLHb0mLpaulxTfgZ+fTohvqUVqQcpB/7+3XfP8jnEDfsp3oyFqTeZtOmAFjHNIOa8JSb6T9LnzCbes9U1TVMqaxZAgMBAAECggEAdOPzuUgIvpY5DE0euTY7y86gX5wpZO6hWzImxl0i7hookW1pekXwyfWFMqwmZJ3QLlb0Yxdd6VK3fzcy6kqewyXOn5M1QhT1LDwdtuwdjr4bBbjCWzYHYDTfPex9lqXsZ12ZXDu28zLVoHj/Zq9TKVP1jEqW1vv6hqc1tKOrWK2tw3F0JIsmqjOFaYo5kPrlyRNnI8UdEwtQ08Ynh8e6GAr24ZcjP1YkPO4u9WVjIdmqdbn1L8POuFple5DGpiCm/rkb951W4Z9rHN7/0/crjUFc3fktZVhMqA4Ku/LosPzzyCncAW9lNlag4wPlW5QLAH95VTpmGhJVV0JmcWExgQKBgQDfDc9HPXX33B6/Vt+lI2pw86zAzeOUBlch5XIzLQGdgeD5BkObJ9Bw3S7GLs1jBy80nvGRyTmsqP4bvU7RdcwCjQvgFtZUZXygaLJCXa2DHXPyambWRwOphWrcZoH5dpw098TVLhf5nfEOlOULxsQCnBAAzCt/DxgME7poWaM7FQKBgQDan5nsI6XKO2JIMoDlbO6Z+uNA3lXvqmi+idvfgwkjtpQUO3Fv/nc2tLXNd/w/3qp1BE+sY97gV+vCHLNYyt7eD5OrtfPFR1wiS7jfsakkU504CmctgPz619qcFDPFilPB3BKJMAGbaHy7i40v0v5w+Jn7zp3Wl+Re3vlOenztNQKBgFLFzSWiqSRRixd2JYginF1OD818mfdUYwExJC/Zg7/vcoz2xh/Npow7iCADPjSBdgMQ+JQhGEX6cFHa7mTMUeigbse5sc+f2Z+tOnlmSf1d8KkE0ySxNaqy1/TyFUl9unnteB2j/UGkWtAOcPDt0c8mGsow+fMspr/Uklgm62wBAoGAZXenx9pvqyHOprWR72mUgDDaYpQOjJKp1K1FOEKn7kRiC9Xe+1y+VcyfiIKEt9wPfwoGNHuRyMvlpw0NDEv0DlpqbkqfnlNVZrGAV4osd6i7EnR5mLwCLiYa10/lC2z9HL+YdsWqKUfghGbrm2OxJhur1Bv8U8nhe0q/qPXen3UCgYBlhd7LIwPjIW+26mGyef87IyIZselaYDwoLKDymE+vWTaiCLXE11xpw9HJjDG6Y52EBgSfqW/7Tklhv2Vl2js30Pn6tuFJPEYiXWv3LeLc5kzfg9Uil3U/CYyXR0EVqO5Qkv+Nx82SwX4okA+yg01nSdbTcpEGRUCxn5tNtNn3Rg==";
//    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt71xdpbifbDazfDLiSFhbUpq8HQhiXwfNp/+uN8GEuvtSLIcRDFuC4iKHeNGEVryfRugaB6nWWfwhq4GMLaHsYnuMQEpPnImmN+UVzRUeyCQJqQ8zuR0D17qxnf2/BccL/MZlS6HTi5kMwM9QO5PFVPd7pJo8TeFfQIslx5wq4QGKUlemaVskRWpUsTvvKVsel8n5tUKun0MRY/Hcyb5qOdgDgmn4y3TqsTQbK9n30/ySkMVMrakJSRfrYs5e/3fItgQeomjUPjV7X+ySYZtmRvJYhboAXQ95v+4shOuk6/P0aX4z+TzN7YDvwwNaP8p7zmcFBsMoC98aT6eT5XBGwIDAQAB";
//    public static String ALIPAY_NOTIFY_URL = "http://zf.zzjhmjg.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wxec034365dbe82597";
//    public static String WECHAT_OFFICE_APPSECRET = "78ceed0b0b5acef033a98fdca63053c9";
//    public static String MANAGER_URL = "http://gl.zzjhmjg.com/";

    //大众互娱
//    public static String WECHAT_APPID = "wx979cb7dabada9cad";                       //appid
//    public static String WECHAT_KEY = "PXlR53KwXY8V3P9Mgr989sl9AdW1eIo7";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://dazhonghuyuzf.chuangmikeji.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1496695292";
//    public static String ALIPAY_APP_ID = "2018011301839629";
//    public static String ALIPAY_APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJYMKxUnJE8/gpNVePe0INGDglRrrAgOcUAjG4b2dbNIksyV8twjWDN3ysIUL8PRfgucIFyLtYfjrBjigFrQ4jwb5oqlcropLx7oFBc8gkstBFbn+vUTfCoCJtBjYh8aFTSREmlr+dD5SSiIdz5tJVk5d7/3gAL2kIGc3tIIk0405xYE49e0QtUe9kI6THP4pE1aYyNVEzz8r+n3K5XQ4057yf/iwxHnUSbRngVTBDAQb3wJ0zdX17CWZvElZyo8aIgs3NkpY4pwvTtVvWzzwC9f8TtJ1c57eXibBxa/SoIaCVDFo08erGBF2BrOn1FaXtqvkjIraUl1GUXrDzhk9ZAgMBAAECggEAfA+VwuzhpMNt6Ejzue9lxs2IZqFpJU9DXx5cKGLyw7q95mQ0P+6SxNznCcs/4uqJFbirLu4HwfK8vO7T1cxiaIgenAa4kf9PFIx+zczxgbK5wvn29SwzXt+eNzbUy/KZ7cyeYGriv8PCOFJzgIeCiyWuN5yNkh0+jMJA5VPslIUfFtYxp83HXyVjJaThmTsT00uc1H1vyrY9XlWoiv+U3SpwLLXrvMV15fTNTlQbHN+v9YWoOMywA2lfaI2n9CdcOAVRhgTLSfrkrKUO10zSDMGudZeGRmiudBx3AjWebBEsbJ8ONRrT2URYINlNGy9C2Tc1UjVGNYcXZPg2HhHmwQKBgQC/oyGq/pCPPbIkZbhTcCaFreXZdSNQD+iOsQ7wiV4YtarMT0dn2rKE1xDicbXK7n5n+ham2RpRsbx637crqbHt1RtR09kcXGvojixk80amCiVTJqDtZFetNjtdsOWfxtCcXFPhEELoEls3dlwCJqILxEZnL9M41yby/idEfZp5wwKBgQC3hHI645CaBwfNcAYavk0TMxJ47Wb5JWZV49715nZGI2b1nQYcop1Fp3xiU9VEly4QDdWSs0RdD9oXkWA3lKBjHogKN8bT1fm9/OTSo74cv1s/GY07/MnyT0PBa9QwDeNavbBChTCHbMGkZTX+Em3vhw6NEK2T1v0DY0f8URFkswKBgQCjHBFa/mNCWCvZCO8zrYBMRbAeP7J3waKaWR+zIeiCNZYTYeMQBTfvEzKe2lP2Troue9Ft39mj8RjQqLu6ISx9luYy5pKjrhJ2lRQ6NEpMptQQCw/sHnz1w/QRkbBQJyOGApuuM24PIxiag1ZaylEgnMWjrDQm7BGNvz7i79lciwKBgG5l9dXpSfbu3cm/Z1y1OGahZa5rmnOtBQgMYfdpoaMF7w04XhHh3VymqdP4/KYbkDFGMUHkJ+8X9yoQYWJuPaIDHvGOpkSPR4L8XKc+q8Jdd0XZWDZLFy12c5srA+W4pRnUXPFXcF83lLIR1M44Rfw/Mgi4jMMQJPrhwc9O8XizAoGAHQKnrOb8S3Evh182AK15MvqOQaBYASBOVQ9WUW71awr4CaXz6gu9XZl5DuNRkjEHDDWfrEeKZYb89mst8XG5Cwid2Q68Dd8AZcu1xc/ZgUSU/Ckg6KZ3PorAKp8SBA0NspwWkcNQ9C7Vk3hZDC0iGPkrO0UJpru1NmlnsnbVUyc=";
//    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxykllu7zH6GpFcgZ83KSottFgdHpiZM9Mo8EVTTSXSHAVKqw+Wl24MzAOxQjv1TYblGSEgIGBnZ+60QH1x/mWI1nawCoUgJswQdaL8HQEZFPvmm6DprV9QvLnYslC3rQSyn/NO1xKjQ81XgD+MN7FVAmb7Q0MyW7HoslVhtONXTXZFypkugowLhH2RnCZ89LXToZy2mckdZwyTQvm/A5sc/EtYS/ETZMAG15GTgWHqp5wPMOc65w0UL5hRXFj7xwLjMZ4VsPJwr21QgU8tjIs864mpyaEGN7imrFIg4GMXwRIoG2HXiSZb7bsKbRrjw9GtdVE0gvjfWd8vi5QXg7xwIDAQAB";
//    public static String ALIPAY_NOTIFY_URL = "http://dazhonghuyuzf.chuangmikeji.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wxbb4693c82ec025a5";
//    public static String WECHAT_OFFICE_APPSECRET = "91738db92b736e00fe8dba8fe1e68ab3";
//    public static String MANAGER_URL = "http://dazhonghuyugl.chuangmikeji.com/";

    //山城互娱
//    public static String WECHAT_APPID = "wx5a9a00e9bcff1a32";                       //appid
//    public static String WECHAT_KEY = "Chuangmikejijituanyouxiangongsi8";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://shanchenghuyuzf.chuangmikeji.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1496689412";
//    public static String ALIPAY_APP_ID = "";
//    public static String ALIPAY_APP_PRIVATE_KEY = "";
//    public static String ALIPAY_PUBLIC_KEY = "";
//    public static String ALIPAY_NOTIFY_URL = "http://shanchenghuyuzf.chuangmikeji.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wx2d4606a7e7af7ba9";
//    public static String WECHAT_OFFICE_APPSECRET = "3834e71b6b4de2d202b2f0409596d0d4";
//    public static String MANAGER_URL = "http://shanchenghuyugl.chuangmikeji.com/";

    //西北互娱
    public static String WECHAT_APPID = "";                       //appid
    public static String WECHAT_KEY = "";           //微信支付key
    public static String WECHAT_APPSECRET = "";                       //appsecret
    public static String WECHAT_NOTIFY_URL = "";           //微信异步通知地址
    public static String WECHAT_UNIFIED_URL = ""; //微信请求下单统一地址
    public static String WECHAT_MCH_ID = "";
    public static String ALIPAY_APP_ID = "";
    public static String ALIPAY_APP_PRIVATE_KEY = "";
    public static String ALIPAY_PUBLIC_KEY = "";
    public static String ALIPAY_NOTIFY_URL = "";
    public static String ALIPAY_UNIFIED_URL = "";
    public static String WECHAT_OFFICE_APPID = "wx24242b2bf731f7d7";
    public static String WECHAT_OFFICE_APPSECRET = "81d11ec24e8006c0fbce680da6bbbb2e";
    public static String MANAGER_URL = "http://kphygl.goodug.cn/";


    //只用于支付  南陵
//    public static String WECHAT_APPID = "wx534a454b89d5ac2b";                       //appid
//    public static String WECHAT_KEY = "ikQnfVFWYh8lyzQ414wVjgIV9ymll90c";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://nanlingbuy.nlmajiang.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1493804892";
//    public static String WECHAT_OFFICE_APPID = "wx34e46b11c9b3db48";
//    public static String WECHAT_OFFICE_APPSECRET = "c8235fb7ff5f3cbc403f799d27397679";

    //牡丹江
//    public static String WECHAT_APPID = "wx65533b48dd308c88";                       //appid
//    public static String WECHAT_KEY = "TJdQ4OI3lcJnkL9Jqv2a0YtXXMNjUnYo";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://zf.mdjpoker.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1495456312";
//    public static String ALIPAY_APP_ID = "";
//    public static String ALIPAY_APP_PRIVATE_KEY = "";
//    public static String ALIPAY_PUBLIC_KEY = "";
//    public static String ALIPAY_NOTIFY_URL = "http://zf.mdjpoker.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wx15468dd1bb6be97f";
//    public static String WECHAT_OFFICE_APPSECRET = "e452c2420fd5ad0a8e56ff1868f2d928";
//    public static String MANAGER_URL = "http://gl.mdjpoker.com/";

    //江城互娱
//    public static String WECHAT_APPID = "wx00d1d0a7a42c6c00";                       //appid
//    public static String WECHAT_KEY = "jingsaibang2015yubangkeji511yl62";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://pay.jingsaibang.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1288955901";
//    public static String ALIPAY_APP_ID = "2088021773617637";
//    public static String ALIPAY_APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDSID6QfESOdTT+xhUGqdkTFeiemZqxMYWtFBpU90FJZRZiW3D+/Z+gLnmA210HsF6t+NPJoZlxlnJvHUzN6hbjxCG69j2D3eshVu+6U5UmfesDc+jTl8JwcfkE5cTVQSzM86hAP7Gc7DJRKP1ttwpjDiS8ZnDcWlVQI6MU2xeEiu9BU+Hobtmm3tG5NBuw3h8BRFhQbtDY1Mjn5HP5JxGU8+YOWAs4wzTl5kRBlH+Xhf6P0CIhy+KEMBlEAzZnPcT5sWmUIAG3t9UxN+po+uhGLxCmsqBZ1mT6ONjXsQCiwoj8UiOuvz131r6jjX79wI6F8VzckIqZq3GmGEvmWQ4RAgMBAAECggEBAMfOHW/qmo4T6Zax6GBztZSuaooVacFAY+pWiUtt6cjOtOBGL0ZpCRaZ2rOq4+Vh0kX/q/O5FF2sP5hk9hl/lNB0Oda8O3Ze2SzUrBQzUkpNxeF8oFJDpoKh/baofeiClwU0VDLz3gJnntkJ7yIawWe7lTcI+hyuUR1pJbO5Ob2vXKorjDUD97M4wW9KVTnX4taeNceHHF90NRMb5mh2uzIrcvb3TxFabHte7OR0G3b8WfyVJ7ZUOh1R6qD2l594hI9rCSgo2ObON4XL0cJO/2f63KYbdRD9DCoR1BbajbOJr9a6cd8qt8SJ7V4E9MpWJ3+JVhNC3CTI9zLGurhmQ3kCgYEA+u6pbXe4ZeQM03WhsRoGkr6oYh5CkZCGMb1NehOy7Au6/fXa/JZ3TClcCSKyYMS04qjcuO2d28OPwRaiQY/LMx+QtUpZoGycEcaRPN0xVygJ1rjlIEbtIaWD6IlxjGGDwS+ddZmowwU14yPBdrMj30rFw1ggSg7vKknpRCHVOe8CgYEA1l6cZVZMO9jFeC4ME15FUWyDrH3z74c0qsW6IDjbafqsYHUOY3Xuv9rZ15hxJtqsCOUW3qLbVM47VowBNr3DlCDNhAGp6m1EAwY0tVvedgzsee9poYLHW/+sCC/BhKUvrqHuuRn9g7XNkaFKwGFf3vNTq5s5wiIXx5IWtH6eN/8CgYBGF1gYDbLiMxsqDdeGYqCnaL4b+hvaESaKyjfT+6Cksnoe6gerOlREX+EnZBuzT/6+uTA+L5FCi5Oh+bKE4ad+tjhzizsRxoSpqm9zMs3PbI2i/q5xn54pIdSApbOj5LEqyyQOb0dmghQVSC2H/G3utzIaaIlMZnODunV/5Hts3QKBgBcm5LE1Hsqf4MPYBUnaNpZSp+3rNHs8GP4QmMd+HIRXpGJOgh1P0Vc7JafZOO8/UrErPMsSv6lrFJGGUWM+H29SE6AcJePBpUTGWM71exM41hldOAVPgIR5GnVP4xCWXo33wzaOjgi5i9gcmVwpqBcGJJU2EIjc8cCGEJmxyxi1AoGBAORkERtc1PxZQOsBgXRPhUDY3bijJhFsCm3VY7Q30bXdLLgohu8R4/kje8/N7r0Ql5TiL5hwgnvDTyd7qvQOFwYVhrlwOhnGmia9bFBw660c3ecBgEBtGx04cJHNPyiO+2AvSRIwwxU0ZKFyzqhOf8xiWkLICv9I2tRH5k1h663r";
//    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmloQ2Pd6o0VtM9RPkvjRC75oTRsL/fpt8k/q4IH2XsiGzh5TOLf5y/1IoEYZ7YKSJfUrOBAopWQ6XUrEotqY1ZbgoDYBTy4KWWYbMy711ngyOStYJVFxwkv3rZo+GhHJW9GM2WL/MHNUN5G565aBLIvwvYHTHR3OMG9bE2kk+Vx8VwTPaF2lo/Q8sPcv2i/ABbXAffIzdduaAf1OI4ylxIUvsenqp4FTyaH986s8gj3E4UAfYAnPTpjrPrGFY0PLfQzYNrWTAFX9u1vD8aDOEezrNujzT3DU7TinmF+laGdy8dSzdKuBB2tR8TyLDPX0tgexccgpLzotzR97vS+PIwIDAQAB";
//    public static String ALIPAY_NOTIFY_URL = "http://pay.jingsaibang.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wx5f92b5138fa881da";
//    public static String WECHAT_OFFICE_APPSECRET = "38dccbd677ff14f949a95d53ca7c6fd7";
//    public static String MANAGER_URL = "http://gl.jingsaibang.com/";


//    //盛天互娱
//    public static String WECHAT_APPID = "wxec1e184b8804b33e";                       //appid
//    public static String WECHAT_KEY = "cp9D6GFlVw78aFo3tx3s2NW2APPfj4Gs";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://zf.zmbaobei.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1498273382";
//    public static String ALIPAY_APP_ID = "2018020102125609";
//    public static String ALIPAY_APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCypf62B+A2KdQe+qO+Owko9J4zmPXHiOYqI7/1IHomfwpw4AIo5KOIGxlnshV6jHDTkHRrgRbHgh9BVRFst0SI0aLyejfjPQ8JYsOdUqDtubidAPCJ6elVUUUXQ+DIpZXdB3ArAGo3W0M19qI1bzQT4BJXT/SprEdPSUQ9skxOLSMRuFo3VJZvzSVsx0UnaFpj38230/3jRtI8kgbxJRtFYa0IW/NhQZJzQouq/v5r2RIhwS6WmjoqeXTxtEXzaBpIgSLCSE3d6LY9Xr7IbC05B80mRinMPMW/pGrJKpgRu3Kmzjw4rmFYFsCWPkltSZ39JoTUGbxfO6XExk6oL+SDAgMBAAECggEBAKdK0MIvj+PIuc4k0c9jQ0mqALe6xnYLGdZeUfS/bAO+tc1LyFn4+baMAfdcCccP2twTiyHc6bL8hD5O5hlTaYdiIOBGWS2f0QPuC9a4OOFFKL9F0PqlJA70IuoNc2MRxMwNmhl6svyRptfS1MWLFE6EZ0iCJE/7d2+k4ZWQ8OnGvooOVDbTCqbEOYSX8r4UZFJoImT9l+YsONhPqnb/ObA2tKg4Rr1WThIDWj6tuAFpTY4UruPtMrU2R39+dQu4sdQtiE4VSVRjpVD3BH6EK+uGwY1hovotQVkKoEzUaNL7p8KdG+TzCmdaRG2UsGNHI+rsS/8fcRfG82DCYtCqTWkCgYEA/bqYN3ZzuxY2qb+mdzKHdLY63JYsDkxH4FAiyA/ywZVHRRyD8G5vhV1w4ehOk4f88ppn/Ll9dN1B9xuVxtKSoNPnzuuxTdtdExhzG0s9I8WiRAPzsA5w6TZn6+6Z5ys0Ms2UjiOMFthquVtvkczFkmVYTRNqYFaoYYys9PgHP9UCgYEAtD9bk43x8NIZQxrsrHooI1Tq8kUMypHvfod9aZdvm6KYPDwp11FbDJIhwILL9DXeCbcRU6HnYyTTXAY9ch34ILKvDudyrrp0zfXpgEBZoxl+KsxjKvU2NkkfbIgC3dQX1n3ZJg32suw1LuVOi1gWIAyNa5vyMjgNq4LnpGJhFvcCgYEAmfmRhB7rDn7lf0/hmnZcg3RO9ixf4fFisPqQkO40uEt567PpUZADN2DELyPGA3vEAgm3z0NVogTfkjD4TKbe+qB+DTE08PWNXxg4Sjj3imzWEwZ+7LlmxfARUrkCRqoOTEI7w6JI0lhVM9UMOOOWhkfgNS3yITKVjlVLZIHcDF0CgYBdXLv4b/uzu3+uTJHuvM9xjZisqKt5eaHNrix9txzuRogIGzmo99Drb/3fk2BFPGmoKpTk9uW9auO3tNu68l8FwMgANk4mxxmdXRT3g11qp9e8H2X2aFEOSU3nj4O8lxFJ3qHNnkn8X3Eku021y4iaTSYraPlIndQm1IVblkGn/QKBgAdjkjqzSFrd40aZ8weyS2aSnQ2UCO3X9XA01CHNZjLzZtU8YAjoA/zExt44EDW+xqGSm8orI1mbF5VZP24nhyH8cKCBOWpH51vuIft1mXzb8F9R24p5XX2KIcn/h5uRXqeCrrMnPsop8Yzftr+RGh5NHPWqYOns7FqGt1a9RDIs";
//    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh4sX+SianpVCZZaS8jWbHpWHP6JFS6YncAhOHdy4eubErY9Snm2DkY4mVKPKtqFjU8yq6wB44M3rGF6swFfwj4V/jJoCaTwdwjiZ/BC3FF1qA9ut0GSXpcZ32sa5ZiygX80YkmWWNj0ia+L7v2qByhYrogUnC1FwCp/BBmFCehMPBZsWEYtnaOPnjoszFfBgJTBHdJTqQ3QfphVum3L8m7mQIXOcBLrkIvnb3JeGt4CDupo2065VQzgMQMLTx6wfY+MycHoM+766GvB/wnEqTteulzOYPQvhi8m07lutiBueUDt43HoH5rblIXQI14lsI7lX2gdFCcO5TTKfeHcwwQIDAQAB";
//    public static String ALIPAY_NOTIFY_URL = "http://zf.zmbaobei.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wx8f6bc16c163a4f91";
//    public static String WECHAT_OFFICE_APPSECRET = "6ecbbb3ee03ae15c07ff6797f429b29d";
//    public static String MANAGER_URL = "http://gl.zmbaobei.com/";

    //全民互娱
//    public static String WECHAT_APPID = "wx69c380fbcc3322ff";                       //appid
//    public static String WECHAT_KEY = "MgVQuWB0IUaPZ2tkuU0Dx7DItyLohxKA";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://quanminzf.chuangmikeji.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1498403312";
//    public static String ALIPAY_APP_ID = "2018012402053914";
//    public static String ALIPAY_APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCypf62B+A2KdQe+qO+Owko9J4zmPXHiOYqI7/1IHomfwpw4AIo5KOIGxlnshV6jHDTkHRrgRbHgh9BVRFst0SI0aLyejfjPQ8JYsOdUqDtubidAPCJ6elVUUUXQ+DIpZXdB3ArAGo3W0M19qI1bzQT4BJXT/SprEdPSUQ9skxOLSMRuFo3VJZvzSVsx0UnaFpj38230/3jRtI8kgbxJRtFYa0IW/NhQZJzQouq/v5r2RIhwS6WmjoqeXTxtEXzaBpIgSLCSE3d6LY9Xr7IbC05B80mRinMPMW/pGrJKpgRu3Kmzjw4rmFYFsCWPkltSZ39JoTUGbxfO6XExk6oL+SDAgMBAAECggEBAKdK0MIvj+PIuc4k0c9jQ0mqALe6xnYLGdZeUfS/bAO+tc1LyFn4+baMAfdcCccP2twTiyHc6bL8hD5O5hlTaYdiIOBGWS2f0QPuC9a4OOFFKL9F0PqlJA70IuoNc2MRxMwNmhl6svyRptfS1MWLFE6EZ0iCJE/7d2+k4ZWQ8OnGvooOVDbTCqbEOYSX8r4UZFJoImT9l+YsONhPqnb/ObA2tKg4Rr1WThIDWj6tuAFpTY4UruPtMrU2R39+dQu4sdQtiE4VSVRjpVD3BH6EK+uGwY1hovotQVkKoEzUaNL7p8KdG+TzCmdaRG2UsGNHI+rsS/8fcRfG82DCYtCqTWkCgYEA/bqYN3ZzuxY2qb+mdzKHdLY63JYsDkxH4FAiyA/ywZVHRRyD8G5vhV1w4ehOk4f88ppn/Ll9dN1B9xuVxtKSoNPnzuuxTdtdExhzG0s9I8WiRAPzsA5w6TZn6+6Z5ys0Ms2UjiOMFthquVtvkczFkmVYTRNqYFaoYYys9PgHP9UCgYEAtD9bk43x8NIZQxrsrHooI1Tq8kUMypHvfod9aZdvm6KYPDwp11FbDJIhwILL9DXeCbcRU6HnYyTTXAY9ch34ILKvDudyrrp0zfXpgEBZoxl+KsxjKvU2NkkfbIgC3dQX1n3ZJg32suw1LuVOi1gWIAyNa5vyMjgNq4LnpGJhFvcCgYEAmfmRhB7rDn7lf0/hmnZcg3RO9ixf4fFisPqQkO40uEt567PpUZADN2DELyPGA3vEAgm3z0NVogTfkjD4TKbe+qB+DTE08PWNXxg4Sjj3imzWEwZ+7LlmxfARUrkCRqoOTEI7w6JI0lhVM9UMOOOWhkfgNS3yITKVjlVLZIHcDF0CgYBdXLv4b/uzu3+uTJHuvM9xjZisqKt5eaHNrix9txzuRogIGzmo99Drb/3fk2BFPGmoKpTk9uW9auO3tNu68l8FwMgANk4mxxmdXRT3g11qp9e8H2X2aFEOSU3nj4O8lxFJ3qHNnkn8X3Eku021y4iaTSYraPlIndQm1IVblkGn/QKBgAdjkjqzSFrd40aZ8weyS2aSnQ2UCO3X9XA01CHNZjLzZtU8YAjoA/zExt44EDW+xqGSm8orI1mbF5VZP24nhyH8cKCBOWpH51vuIft1mXzb8F9R24p5XX2KIcn/h5uRXqeCrrMnPsop8Yzftr+RGh5NHPWqYOns7FqGt1a9RDIs";
//    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhtgj8d4ypsXp6fBwS69FxFNGP2LSZ4NJSypJtHau6v7HNddwh012/9f0k1QnvxfJnk02paBh6T0+uJg5NUGaPmTMLtDVHQXYnx/Ibiqcu7Dl6kQf9Rc9OgwrzY1g8r+BipeFyIXP6peCIexpj8MazkYwMoAvZ+fzRmWZ4IRgpCHeLpnqDMiCmaZ5nn8B7yCxBXI6Z0cpFq+BAQDbdVuvPYSsmsPqTy5BctGy4DEebk9GJbvRCdYwh4DbiueKWJy493+U2p3+4GlaVhpakv1xVWx4Yk4JACi7HSRCwZj2nBstVWapEZdiy/x+QVno27vKVRWBOm/NJAPMo3QtUfQI2QIDAQAB";
//    public static String ALIPAY_NOTIFY_URL = "http://quanminzf.chuangmikeji.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wx34e46b11c9b3db48";
//    public static String WECHAT_OFFICE_APPSECRET = "c8235fb7ff5f3cbc403f799d27397679";
//    public static String MANAGER_URL = "http://quanmingl.chuangmikeji.com/";

    //王牌
//    public static String WECHAT_APPID = "wxc7218bbea4ac6b49";                       //appid
//    public static String WECHAT_KEY = "Cmsy1234567891234567891234567891";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://wzzf.hi528.cn/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1498320552";
//    public static String ALIPAY_APP_ID = "";
//    public static String ALIPAY_APP_PRIVATE_KEY = "";
//    public static String ALIPAY_PUBLIC_KEY = "";
//    public static String ALIPAY_NOTIFY_URL = "http://wzzf.hi528.cn/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wxa9289774de2a4694";
//    public static String WECHAT_OFFICE_APPSECRET = "a232120a691067164241715f325f9fb1";
//    public static String MANAGER_URL = "http://gl.zhangshangwanzhou.com/";

    //牛英雄
//    public static String WECHAT_APPID = "wx53861726841f7efb";                       //appid
//    public static String WECHAT_KEY = "Er5jCOUrhgegbfe1QBDD1ETvHQnB8cUm";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://niuyingxiongzf.hi528.cn/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1499701372";
//    public static String ALIPAY_APP_ID = "2018021202184373";
//    public static String ALIPAY_APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCypf62B+A2KdQe+qO+Owko9J4zmPXHiOYqI7/1IHomfwpw4AIo5KOIGxlnshV6jHDTkHRrgRbHgh9BVRFst0SI0aLyejfjPQ8JYsOdUqDtubidAPCJ6elVUUUXQ+DIpZXdB3ArAGo3W0M19qI1bzQT4BJXT/SprEdPSUQ9skxOLSMRuFo3VJZvzSVsx0UnaFpj38230/3jRtI8kgbxJRtFYa0IW/NhQZJzQouq/v5r2RIhwS6WmjoqeXTxtEXzaBpIgSLCSE3d6LY9Xr7IbC05B80mRinMPMW/pGrJKpgRu3Kmzjw4rmFYFsCWPkltSZ39JoTUGbxfO6XExk6oL+SDAgMBAAECggEBAKdK0MIvj+PIuc4k0c9jQ0mqALe6xnYLGdZeUfS/bAO+tc1LyFn4+baMAfdcCccP2twTiyHc6bL8hD5O5hlTaYdiIOBGWS2f0QPuC9a4OOFFKL9F0PqlJA70IuoNc2MRxMwNmhl6svyRptfS1MWLFE6EZ0iCJE/7d2+k4ZWQ8OnGvooOVDbTCqbEOYSX8r4UZFJoImT9l+YsONhPqnb/ObA2tKg4Rr1WThIDWj6tuAFpTY4UruPtMrU2R39+dQu4sdQtiE4VSVRjpVD3BH6EK+uGwY1hovotQVkKoEzUaNL7p8KdG+TzCmdaRG2UsGNHI+rsS/8fcRfG82DCYtCqTWkCgYEA/bqYN3ZzuxY2qb+mdzKHdLY63JYsDkxH4FAiyA/ywZVHRRyD8G5vhV1w4ehOk4f88ppn/Ll9dN1B9xuVxtKSoNPnzuuxTdtdExhzG0s9I8WiRAPzsA5w6TZn6+6Z5ys0Ms2UjiOMFthquVtvkczFkmVYTRNqYFaoYYys9PgHP9UCgYEAtD9bk43x8NIZQxrsrHooI1Tq8kUMypHvfod9aZdvm6KYPDwp11FbDJIhwILL9DXeCbcRU6HnYyTTXAY9ch34ILKvDudyrrp0zfXpgEBZoxl+KsxjKvU2NkkfbIgC3dQX1n3ZJg32suw1LuVOi1gWIAyNa5vyMjgNq4LnpGJhFvcCgYEAmfmRhB7rDn7lf0/hmnZcg3RO9ixf4fFisPqQkO40uEt567PpUZADN2DELyPGA3vEAgm3z0NVogTfkjD4TKbe+qB+DTE08PWNXxg4Sjj3imzWEwZ+7LlmxfARUrkCRqoOTEI7w6JI0lhVM9UMOOOWhkfgNS3yITKVjlVLZIHcDF0CgYBdXLv4b/uzu3+uTJHuvM9xjZisqKt5eaHNrix9txzuRogIGzmo99Drb/3fk2BFPGmoKpTk9uW9auO3tNu68l8FwMgANk4mxxmdXRT3g11qp9e8H2X2aFEOSU3nj4O8lxFJ3qHNnkn8X3Eku021y4iaTSYraPlIndQm1IVblkGn/QKBgAdjkjqzSFrd40aZ8weyS2aSnQ2UCO3X9XA01CHNZjLzZtU8YAjoA/zExt44EDW+xqGSm8orI1mbF5VZP24nhyH8cKCBOWpH51vuIft1mXzb8F9R24p5XX2KIcn/h5uRXqeCrrMnPsop8Yzftr+RGh5NHPWqYOns7FqGt1a9RDIs";
//    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh4sX+SianpVCZZaS8jWbHpWHP6JFS6YncAhOHdy4eubErY9Snm2DkY4mVKPKtqFjU8yq6wB44M3rGF6swFfwj4V/jJoCaTwdwjiZ/BC3FF1qA9ut0GSXpcZ32sa5ZiygX80YkmWWNj0ia+L7v2qByhYrogUnC1FwCp/BBmFCehMPBZsWEYtnaOPnjoszFfBgJTBHdJTqQ3QfphVum3L8m7mQIXOcBLrkIvnb3JeGt4CDupo2065VQzgMQMLTx6wfY+MycHoM+766GvB/wnEqTteulzOYPQvhi8m07lutiBueUDt43HoH5rblIXQI14lsI7lX2gdFCcO5TTKfeHcwwQIDAQAB";
//    public static String ALIPAY_NOTIFY_URL = "http://niuyingxiongzf.hi528.cn/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wx8514243d9a7ccf1a";
//    public static String WECHAT_OFFICE_APPSECRET = "d7a5cc33d4bb62e25ca1060df44b8822";
//    public static String MANAGER_URL = "http://niuyingxionggl.chuangmikeji.com/";

    //九九
//    public static String WECHAT_APPID = "wx6b7f3e78c2f722a4";                       //appid
//    public static String WECHAT_KEY = "k00LvEtm0se2HUAXWayd7LJBeZCikwyI";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://xinjiujiuzf.chuangmikeji.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1500131562";
//    public static String ALIPAY_APP_ID = "";
//    public static String ALIPAY_APP_PRIVATE_KEY = "";
//    public static String ALIPAY_PUBLIC_KEY = "";
//    public static String ALIPAY_NOTIFY_URL = "http://xinjiujiuzf.chuangmikeji.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wx342ac679a30290da";
//    public static String WECHAT_OFFICE_APPSECRET = "54f4e1f63e9b1b716d4456a484609643";
//    public static String MANAGER_URL = "http://xinjiujiugl.chuangmikeji.com/";

//    //抚顺
//    public static String WECHAT_APPID = "wxf6c90cc17ad523b2";                       //appid
//    public static String WECHAT_KEY = "fsg543kfgh7k3swk75kdg3ksfh899qqk";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://fushunzf.anpone.cn/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1497163742";
//    public static String ALIPAY_APP_ID = "";
//    public static String ALIPAY_APP_PRIVATE_KEY = "";
//    public static String ALIPAY_PUBLIC_KEY = "";
//    public static String ALIPAY_NOTIFY_URL = "http://fushunzf.anpone.cn/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "";
//    public static String WECHAT_OFFICE_APPSECRET = "";
//    public static String MANAGER_URL = "";

    //悠悠
//    public static String WECHAT_APPID = "wx61a2618a3eb6bb07";                       //appid
//    public static String WECHAT_KEY = "tk7yP3dFY42Fhd958TKhlPAylvlhmTZT";           //微信支付key
//    public static String WECHAT_APPSECRET = "";                       //appsecret
//    public static String WECHAT_NOTIFY_URL = "http://zf.xingdikj.com/api/recharge/wechat/notify";           //微信异步通知地址
//    public static String WECHAT_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信请求下单统一地址
//    public static String WECHAT_MCH_ID = "1501097041";
//    public static String ALIPAY_APP_ID = "2018040302496153";
//    public static String ALIPAY_APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDN12B4aPNJjGFNmNis8/GgUB08GkgHL8hu0S0zdMIKnMudp7JdNtE0bRHag+2Km/vBB6+LYKy4Hlk7ThG4fPz8uOAiRud6StrG7BFhg7/zeHz1vAG2DpVnnRNlP3mub4mYcGJuFPDyAmVU9F7QhzdeKJiPnvhzERlCz0NZz/hh/86ezDhX2WoybtVjNyRqIue9ENZb8fUmpDdlPN34iO9zvfJbS1qNlbaUyDDpFwsF531jkrNVR/0TSGp6JBj4fKpyiJPfO15A4QsF7mogbN7DRzOhJEo0j4x0DXbinVwneNDCxe5J3YIB5DFLw4/hXKCA2PX9oWTooXELfRe8lhURAgMBAAECggEAYGVDbUAg5smR68fveJdJCX1bI4FfR9ZNs+/LjjRYhqXFCRC2i8aXFEkbukxcGrRPoqcZw1jDqfm0afx2egI2a5echxDTaEbTy6JTxWnwo3FCe8XOu1Z7CynZImombtUXB8dhoRG3gG40zCMgn+EiwbqbL28QBLr2LH5pmQ620BUnp2YC5ltjJaup6UNdew5IbJKL9mwHpuLDfRAcdyXF0rLIf8EkgPPxTDxwyVQGoMD70A+xWA+podAtwqPjmxFI/6crH1aJ7Dw74wzAO9zGoZZv+xDAU4iKOylcf8AbDSBxAYUE8zeIJH+XuYtJFhrJn95npwFPlIAG0wq77tHF0QKBgQDtzyF1t39GrMn1p8ZHTEXEk+aJq4piWe1emTHnnhK4QwHVV9sRZfRoMzLn+2q7coQRxFJi0u/UTu+JjK2iLJkC46Ws7Xe9SiyxGNm0J4du8ZeRnJtvc68gSpA5vs3/eyEZi88rgynl0r+In49wrac6msR9No6AuRDMTJYz94MQtQKBgQDdlj2YrOUXOgQROsI+ITXqysji8IPiBE4PvfU2Xra8Lbw6fJ1EipP/9sjVVn+k3/BZRWMedKPEOffXL8L3sYgvLajqehIXosyKLXg+fNiA+VKqVooLRZg/fTJWbfMwa9G89CrUwgKD0blQY2Bd4gYxeAJXjRpLIE/uA6mD7LsYbQKBgQCG4hkkX7P9LjxO7Z51Cjn4SbtVEssnBcKxCUtZCDut77O2gmJq990szP1uvxfGwilOun/QNbx6O1t6NSS2+3y+OsjZ0Mc9wjifKm0w1crnjnhdyuoOyRrnweMUHblR01Du4HHdSEQXavXgVEBTtOtJPkd8jdk+AboZZsc7KoRYkQKBgG/3CXrK6hs5YnK+SQYRy+euvaEGPO2IlVUY+/2UECu3xGOdMe4/Q5dTwJkj0u2hhhXlnLb34wQ08kmWRaLY3uJP5QlN0zSEO2wam6afeNp6oM2XWrtlWA/EmBnaBsbRxDIBAl9tBM5Mu6GmBTyLB+wZIr3Qe5e4ZQr0aMRTaxplAoGAI8cAZHzjTuaurO0qTtpLQEvF4DhD1vb5VIBwZla8Q/rI91STM8oxqq31idf9c2ao8E3DNCH6yMqyiK0xjPFJFYglYqTpDis1MwByluytRC1Ux73O+GhVZpWhumN5SWuyacrmw1GZv/oDUxRvKQQQYHYgxzZrh9dt7XtEcmQ/d0o=";
//    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhDpM3Xbr3O76WgwJd/De+qlugtpO+Aov/kp/yxMMo0mmpKCU4ICT4FcKWbJZccK+U+n7PYdu3TlX1/hr8WHu82suT4Ez7Hu10cW7gfuMCQpgIAsmYrt2VZcKXQHN/Pq2fmRRCrZnozeCIaMKCw+bMaswDddVYebHeKpS8UstZqfG7c0T0wyzp2pRUQ5jN4PuCyjFWSQvcRDu+vHRIJx2jW7YYe+G3ROQldQa6Hh11MldizA35FJiVKSLPWBvVXGNI0RTj9CwpL/goBTkrCCzlCNPl8CioslAOnSk2WfpHSdD3+xWoTvjPiwx5jNo4BPncOcPmeuCtd6mxs9Qf9tFawIDAQAB";
//    public static String ALIPAY_NOTIFY_URL = "http://zf.xingdikj.com/api/recharge/alipay/notify";
//    public static String ALIPAY_UNIFIED_URL = "https://openapi.alipay.com/gateway.do";
//    public static String WECHAT_OFFICE_APPID = "wx8a6996423885997d";
//    public static String WECHAT_OFFICE_APPSECRET = "f4cfc02b32eb2a461940bb692fdd3f3e";
//    public static String MANAGER_URL = "";


    public static String CHENGFUTONGID_WECHAT = "";
    public static String CHENGFUTONGKEY_WECHAT = "";
    public static String CHENGFUTONGID_ALIPAY = "";
    public static String CHENGFUTONGKEY_ALIPAY = "";
    public static String JUNFUTONGID_WECHAT = "01018086996601";
    public static String JUNFUTONGKEY_WECHAT = "040109111702okexlRUE";
    public static String JUNFUTONGID_ALIPAY = "01018086996601";
    public static String JUNFUTONGKEY_ALIPAY = "040109111702okexlRUE";
}
