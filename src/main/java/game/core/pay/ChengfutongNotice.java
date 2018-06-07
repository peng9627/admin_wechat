package game.core.pay;

import game.core.common.Constants;
import game.core.util.CoreStringUtils;

/**
 * Created by pengyi
 * Date : 18-4-26.
 * desc:
 */
public class ChengfutongNotice {

    private String p1_yingyongnum;
    private String p2_ordernumber;
    private String p3_money;
    private String p4_zfstate;
    private String p5_orderid;
    private String p6_productcode;
    private String p7_bank_card_code;
    private String p8_charset;
    private String p9_signtype;
    private String p10_sign;
    private String p11_pdesc;
    private String p12_remark;

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

    public String getP4_zfstate() {
        return p4_zfstate;
    }

    public void setP4_zfstate(String p4_zfstate) {
        this.p4_zfstate = p4_zfstate;
    }

    public String getP5_orderid() {
        return p5_orderid;
    }

    public void setP5_orderid(String p5_orderid) {
        this.p5_orderid = p5_orderid;
    }

    public String getP6_productcode() {
        return p6_productcode;
    }

    public void setP6_productcode(String p6_productcode) {
        this.p6_productcode = p6_productcode;
    }

    public String getP7_bank_card_code() {
        return p7_bank_card_code;
    }

    public void setP7_bank_card_code(String p7_bank_card_code) {
        this.p7_bank_card_code = p7_bank_card_code;
    }

    public String getP8_charset() {
        return p8_charset;
    }

    public void setP8_charset(String p8_charset) {
        this.p8_charset = p8_charset;
    }

    public String getP9_signtype() {
        return p9_signtype;
    }

    public void setP9_signtype(String p9_signtype) {
        this.p9_signtype = p9_signtype;
    }

    public String getP10_sign() {
        return p10_sign;
    }

    public void setP10_sign(String p10_sign) {
        this.p10_sign = p10_sign;
    }

    public String getP11_pdesc() {
        return p11_pdesc;
    }

    public void setP11_pdesc(String p11_pdesc) {
        this.p11_pdesc = p11_pdesc;
    }

    public String getP12_remark() {
        return p12_remark;
    }

    public void setP12_remark(String p12_remark) {
        this.p12_remark = p12_remark;
    }

    public boolean signset() {
        return p10_sign.equals(CoreStringUtils.md5(p1_yingyongnum + "&" +
                p2_ordernumber + "&" +
                p3_money + "&" +
                p4_zfstate + "&" +
                p5_orderid + "&" +
                p6_productcode + "&" +
                p7_bank_card_code + "&" +
                p8_charset + "&" +
                p9_signtype + "&" +
                p11_pdesc + "&" +
                Constants.CHENGFUTONGKEY_WECHAT, 32, true, "utf-8"));
    }

    public boolean signZZset() {
        return p10_sign.equals(CoreStringUtils.md5(p1_yingyongnum + "&" +
                p2_ordernumber + "&" +
                p3_money + "&" +
                p4_zfstate + "&" +
                p5_orderid + "&" +
                p6_productcode + "&" +
                p7_bank_card_code + "&" +
                p8_charset + "&" +
                p9_signtype + "&" +
                p11_pdesc + "&" +
                Constants.CHENGFUTONGKEY_ALIPAY, 32, true, "utf-8"));
    }
}
