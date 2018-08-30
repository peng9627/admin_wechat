package game.core.pay;

import game.core.common.Constants;
import game.core.util.CoreStringUtils;

/**
 * Created by pengyi
 * Date : 18-4-26.
 * desc:
 */
public class JunfutongRecharge {

    private String p1_yingyongnum;
    private String p2_ordernumber;
    private String p3_money;
    private String p6_ordertime;
    private String p7_productcode;
    private String p8_sign;
    private String p14_customname;
    private String p16_customip;
    private String p25_terminal;
    private String paytype;
    private String p26_ext1;

    public String getP1_yingyongnum() {
        return p1_yingyongnum;
    }

    public void setP1_yingyongnum(String p1_yingyongnum) {
        this.p1_yingyongnum = p1_yingyongnum;
    }

    public String getP2_ordernumber() {
        return p2_ordernumber;
    }

    public void setP2_ordernumber(String p2_ordernumber) {
        this.p2_ordernumber = p2_ordernumber;
    }

    public String getP3_money() {
        return p3_money;
    }

    public void setP3_money(String p3_money) {
        this.p3_money = p3_money;
    }

    public String getP6_ordertime() {
        return p6_ordertime;
    }

    public void setP6_ordertime(String p6_ordertime) {
        this.p6_ordertime = p6_ordertime;
    }

    public String getP7_productcode() {
        return p7_productcode;
    }

    public void setP7_productcode(String p7_productcode) {
        this.p7_productcode = p7_productcode;
    }

    public String getP8_sign() {
        return p8_sign;
    }

    public void setP8_sign(String p8_sign) {
        this.p8_sign = p8_sign;
    }

    public String getP14_customname() {
        return p14_customname;
    }

    public void setP14_customname(String p14_customname) {
        this.p14_customname = p14_customname;
    }

    public String getP16_customip() {
        return p16_customip;
    }

    public void setP16_customip(String p16_customip) {
        this.p16_customip = p16_customip;
    }

    public String getP25_terminal() {
        return p25_terminal;
    }

    public void setP25_terminal(String p25_terminal) {
        this.p25_terminal = p25_terminal;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getP26_ext1() {
        return p26_ext1;
    }

    public void setP26_ext1(String p26_ext1) {
        this.p26_ext1 = p26_ext1;
    }

    public void signset() {
        p8_sign = CoreStringUtils.md5(
                p1_yingyongnum + "&" +
                        p2_ordernumber + "&" +
                        p3_money + "&" +
                        p6_ordertime + "&" +
                        p7_productcode + "&" +
                        Constants.JUNFUTONGKEY_ALIPAY, 32, true, "utf-8"
        );
    }

}
