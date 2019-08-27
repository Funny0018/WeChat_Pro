package com.entity;

public class wxPay {

    private String Appid;
    private String attach;
    private String body;
    private String mch_id;
    private String nonce_str;
    private String notify_url;
    private String out_trade_no;
    private String spbill_create_ip;
    private String total_fee;
    private String trade_type;
    private String openid;
    private String appKey;

    public wxPay() {
    }

    public wxPay(String attach, String body, String nonce_str, String notify_url, String out_trade_no, String total_fee, String openid) {
        this.Appid = "wxc6adc19848efee7b";
        this.attach = attach;
        this.body = body;
        this.mch_id = "1501044431";
        this.nonce_str = nonce_str;
        this.notify_url = "https://xxs.xixiansheng.cn/WX_JVXXS" + notify_url;
        this.out_trade_no = out_trade_no;
        this.spbill_create_ip = "47.105.193.26";
        this.total_fee = total_fee;
        this.trade_type = "JSAPI";
//        this.openid = "oyfri5Nt5-Rluk79l0gqgtrxLsoM";
        this.openid = openid;
        this.appKey = "DB169F2D63CF0F34A1A684B6BDA82756";
    }

    public String getAppid() {
        return Appid;
    }

    public void setAppid(String appid) {
        Appid = appid;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
