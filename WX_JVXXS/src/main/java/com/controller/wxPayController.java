package com.controller;

import com.entity.*;
import com.tools.HttpRequest;
import com.tools.printHelper;
import com.tools.printUnit;
import com.tools.publicTools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class wxPayController {

    @Resource(name = "billService")
    private com.service.billService billService;
    @Resource(name = "userService")
    private com.service.userService userService;
    @Resource(name = "amountRecordService")
    private com.service.amountRecordService amountRecordService;
    @Resource(name = "vipBillService")
    private com.service.vipBillService vipBillService;
    @Resource(name = "errorLogService")
    private com.service.errorLogService errorLogService;

    private static int access_token_time = 7200;
    private static String access_token = "16_QjAJwbAn26U1gTMPt-DETDSB5fh_Wg66o_JEnGqjbxl0uSNKPlHEz_ylLWb_hFxaxWwU3NS3QY_-BITP_xRBV82p-foc_lvSPCcIGuBXs4sfeCOHHR_snJ623LoqEHCJaTRO4IVwWXSLTeLYOGLiACAIAF";
    private static Time time = null;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //    @Transactional
    @RequestMapping("/wxPayCallbackBill")
    public @ResponseBody
    String wxPayCallbackBill(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        String httpresult = "";
        String billno = "";
        try {
            InputStream inStream = request.getInputStream();
            int _buffer_size = 1024;
            if (inStream != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[_buffer_size];
                int count = -1;
                while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                    outStream.write(tempBytes, 0, count);
                }
                tempBytes = null;
                outStream.flush();
                //将流转换成字符串

                httpresult = new String(outStream.toByteArray(), "UTF-8");

//                httpresult = "<xml><appid><![CDATA[wxc6adc19848efee7b]]></appid>\n" +
//                        "<attach><![CDATA[商品付款]]></attach>\n" +
//                        "<bank_type><![CDATA[ABC_DEBIT]]></bank_type>\n" +
//                        "<cash_fee><![CDATA[400]]></cash_fee>\n" +
//                        "<fee_type><![CDATA[CNY]]></fee_type>\n" +
//                        "<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
//                        "<mch_id><![CDATA[1501044431]]></mch_id>\n" +
//                        "<nonce_str><![CDATA[sA1iqPiqQrE5wY48ukELFsqx1GbKijJ0]]></nonce_str>\n" +
//                        "<openid><![CDATA[oyfri5IuDuVKxw9ed8QIUK2XE5aY]]></openid>\n" +
//                        "<out_trade_no><![CDATA[XXS1812230001]]></out_trade_no>\n" +
//                        "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
//                        "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
//                        "<sign><![CDATA[FFFD6F6A2BBA251F71EE64E2C020C682]]></sign>\n" +
//                        "<time_end><![CDATA[20181223101830]]></time_end>\n" +
//                        "<total_fee>400</total_fee>\n" +
//                        "<trade_type><![CDATA[JSAPI]]></trade_type>\n" +
//                        "<transaction_id><![CDATA[4200000238201812237689107161]]></transaction_id>\n" +
//                        "</xml>";
                System.out.println(result);

                Document doc = DocumentHelper.parseText(httpresult);
                Element rootElt = doc.getRootElement();

                String returncode = "";
                Iterator iterReturnCode = rootElt.elementIterator("return_code");
                while (iterReturnCode.hasNext()) {
                    Element element = (Element) iterReturnCode.next();
                    returncode = element.getText();
                }

                Iterator iterTradeNo = rootElt.elementIterator("out_trade_no");
                while (iterTradeNo.hasNext()) {
                    Element element = (Element) iterTradeNo.next();
                    billno = element.getText();
                }
                if (returncode.equals("SUCCESS")) {
                    Date day = new Date();

                    bill b = billService.selectByFbillno(billno);
//                    bill bill = billService.selectByBillSimple(billno);
                    b.setFstate(1);
                    b.setFpaydate(df.format(day));

                    Iterator iterWxno = rootElt.elementIterator("transaction_id");
                    String wxno = "";
                    while (iterWxno.hasNext()) {
                        Element element = (Element) iterWxno.next();
                        wxno = element.getText();
                    }
                    Iterator iterAttach = rootElt.elementIterator("attach");
                    String attach = "";
                    while (iterAttach.hasNext()) {
                        Element element = (Element) iterAttach.next();
                        attach = element.getText();
                    }
                    Iterator iterwxAmount = rootElt.elementIterator("cash_fee");
                    String wxAmount = "";
                    while (iterwxAmount.hasNext()) {
                        Element element = (Element) iterwxAmount.next();
                        wxAmount = element.getText();
                    }
//                    TransactionStatus status = this.getTransactionManager().getTransaction(this.getTxDefinition());

//
//                    int v = billService.updateState(b);
//
//                    if (b.getFvipamount() > 0) {
//                        vip = 0;
//                        user user = userService.selectByFuserid(String.valueOf(b.getFuserID()));
//                        user.setFmoney(user.getFmoney() - b.getFvipamount());
//                        int userresult = userService.updateUserInfo(user);
//                        amountRecord amountRecord = new amountRecord(0, df.format(day), b.getFuserID(), b.getFvipamount(), 4, billno, "", attach, "", "会员抵扣", b.getFvipamount());
//                        amountRecordService.add(amountRecord);
//                        if (userresult > 0 && amountRecord.getFid() > 0) {
//                            vip = 1;
//                        }
//                    }
//                    amountRecord amountRecord = new amountRecord(0, df.format(day), b.getFuserID(), b.getFfinalAmount(), 1, billno, wxno, attach, "", "零售单", Double.parseDouble(wxAmount) / 100);
//                    amountRecordService.add(amountRecord);
                    try {
                        int updateState = amountRecordService.payOffSale(b, billno, df, day, wxno, attach, wxAmount);
                        if (updateState > 0) {
                            result = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
                                    "<return_msg><![CDATA[OK]]></return_msg>" +
                                    "</xml>";
                        }
                    } catch (Exception e) {
                        result = b.getFbillNo() + "|" + billno + "|" + e.getMessage();
                    }

                }
                System.out.println();
                System.out.println("=========" + billno + "=======" + returncode + "=====================================");

            }
            //通知微信支付系统接收到信息
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            errorLog errorLog = new errorLog(0, sdf.format(date), httpresult, billno, "wxPayCallbackBill", e.getMessage());
            errorLogService.add(errorLog);
            publicTools.writeInText(this.getClass().getName() + "|" + Thread.currentThread().getStackTrace()[1].getMethodName(), df.format(new Date()) + "|" + e.getMessage());

        }

        System.out.println("====================" + result);

        writeIntoFile(billno, result + httpresult);
        return result;
    }

    @RequestMapping("/wxPayCallbackVip")
    public @ResponseBody
    String wxPayCallbackVip(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        try {
            InputStream inStream = request.getInputStream();
            int _buffer_size = 1024;
            if (inStream != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[_buffer_size];
                int count = -1;
                while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                    outStream.write(tempBytes, 0, count);
                }
                tempBytes = null;
                outStream.flush();
                //将流转换成字符串

                String httpresult = new String(outStream.toByteArray(), "UTF-8");
//
//                httpresult = "<xml><appid><![CDATA[wxc6adc19848efee7b]]></appid>\n" +
//                        "<attach><![CDATA[会员充值]]></attach>\n" +
//                        "<bank_type><![CDATA[CITIC_CREDIT]]></bank_type>\n" +
//                        "<cash_fee><![CDATA[1]]></cash_fee>\n" +
//                        "<fee_type><![CDATA[CNY]]></fee_type>\n" +
//                        "<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
//                        "<mch_id><![CDATA[1501044431]]></mch_id>\n" +
//                        "<nonce_str><![CDATA[bqhxY8yUpkctsExrAneJp9Zhqmwzedr4]]></nonce_str>\n" +
//                        "<openid><![CDATA[oyfri5Nt5-Rluk79l0gqgtrxLsoM]]></openid>\n" +
//                        "<out_trade_no><![CDATA[XVC1812050001]]></out_trade_no>\n" +
//                        "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
//                        "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
//                        "<sign><![CDATA[99D919105E4DF12CC287A5182872F071]]></sign>\n" +
//                        "<time_end><![CDATA[20181205042008]]></time_end>\n" +
//                        "<total_fee>1</total_fee>\n" +
//                        "<trade_type><![CDATA[JSAPI]]></trade_type>\n" +
//                        "<transaction_id><![CDATA[4200000223201812055295997448]]></transaction_id>\n" +
//                        "</xml>";

                Document doc = DocumentHelper.parseText(httpresult);
                Element rootElt = doc.getRootElement();

                String returncode = "";
                Iterator iterReturnCode = rootElt.elementIterator("return_code");
                while (iterReturnCode.hasNext()) {
                    Element element = (Element) iterReturnCode.next();
                    returncode = element.getText();
                }

                Iterator iterTradeNo = rootElt.elementIterator("out_trade_no");
                String billno = "";
                while (iterTradeNo.hasNext()) {
                    Element element = (Element) iterTradeNo.next();
                    billno = element.getText();
                }

                writeIntoFile(billno, httpresult);
                if (returncode.equals("SUCCESS")) {
                    Date day = new Date();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    vipBill vipBill = vipBillService.selectByBillno(billno);
                    vipBill.setFstate(1);
                    vipBill.setFpaydate(df.format(day));

                    Iterator iterWxno = rootElt.elementIterator("transaction_id");
                    String wxno = "";
                    while (iterWxno.hasNext()) {
                        Element element = (Element) iterWxno.next();
                        wxno = element.getText();
                    }
                    Iterator iterAttach = rootElt.elementIterator("attach");
                    String attach = "";
                    while (iterAttach.hasNext()) {
                        Element element = (Element) iterAttach.next();
                        attach = element.getText();
                    }
                    Iterator iterwxAmount = rootElt.elementIterator("cash_fee");
                    String wxAmount = "";
                    while (iterwxAmount.hasNext()) {
                        Element element = (Element) iterwxAmount.next();
                        wxAmount = element.getText();
                    }

//                    int v = vipBillService.updateState(vipBill);
//                    amountRecord amountRecord = new amountRecord(0, df.format(day), vipBill.getFuserid(), vipBill.getFamount(), 2, billno, wxno, attach, "", "会员办理", Double.parseDouble(wxAmount) / 100);
//                    amountRecordService.add(amountRecord);
                    try {
                        if (attach.equals("会员办理")) {
                            int updateState = amountRecordService.payOffJoinVip(vipBill, billno, df, day, wxno, attach, wxAmount);
                            if (updateState > 0) {
                                result = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
                                        "<return_msg><![CDATA[OK]]></return_msg>" +
                                        "</xml>";
                            }
                        } else if (attach.equals("会员充值")) {
                            int updateState = amountRecordService.payOffRecharge(vipBill, billno, df, day, wxno, attach, wxAmount);
                            if (updateState > 0) {
                                result = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
                                        "<return_msg><![CDATA[OK]]></return_msg>" +
                                        "</xml>";
                            }
                        } else {
                            result = "返回信息错误";
                        }
                    } catch (Exception e) {
                        result = vipBill.getFbillno() + "|" + billno + "|" + e.getMessage();

                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        errorLog errorLog = new errorLog(0, sdf.format(date), httpresult, billno, "wxPayCallbackVip", e.getMessage());
                        errorLogService.add(errorLog);
                    }


                }
                System.out.println();
                System.out.println("=========" + billno + "=======" + returncode + "=====================================");

            }
            //通知微信支付系统接收到信息
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            errorLog errorLog = new errorLog(0, sdf.format(date), "", "", "wxPayCallbackVip", e.getMessage());
            publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());

            errorLogService.add(errorLog);
        }

        System.out.println("====================" + result);

        return result;
    }


    @RequestMapping("/wxPayBill")
    public @ResponseBody
    Map<String, Object> wxPayBill(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        try {
            String sign = "";
            String billno = request.getParameter("billno");
            String timeStamp = String.valueOf(new Date().getTime());
            bill bill = billService.selectByFbillno(billno);
            if (bill.getFfinalAmount() == 0) {
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                bill.setFstate(1);
                bill.setFpaydate(df.format(day));
                int updateState = amountRecordService.payOffSale(bill, billno, df, day, "", "商品付款", "0");
                a = "会员抵扣";
            } else {
                user user = userService.selectByFuserid(String.valueOf(bill.getFuserID()));
                wxPay wxPay = new wxPay("商品付款", "西鲜生会员", wxGetRandomStr(32), "/wxPayCallbackBill", bill.getFbillNo(), String.valueOf((int) (bill.getFfinalAmount() * 100)), user.getFopenid());
                String prepayStr = wxPrepay(wxPay);
                JSONObject joPre = JSONObject.fromObject(prepayStr);
                if (joPre.get("result").equals("2")) {
                    sign = getWXAPISign(wxPay.getAppid(), timeStamp, wxPay.getNonce_str(), "prepay_id=" + joPre.get("prepay_id"), "MD5", wxPay.getAppKey());
                }
                a = "{\"billno\":\"" + billno + "\",\"timeStamp\":\"" + timeStamp + "\",\"nonceStr\":\"" + wxPay.getNonce_str() + "\",\"package\":\"" + "prepay_id=" + joPre.get("prepay_id") + "\",\"signType\":\"MD5\",\"paySign\":\"" + sign + "\"}";
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            errorLog errorLog = new errorLog(0, sdf.format(date), "", billno, "PayBill", a);
            errorLogService.add(errorLog);
        } catch (Exception e) {
            publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }


    @RequestMapping("/wxPayVip")
    public @ResponseBody
    Map<String, Object> wxPayVip(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        try {
            String sign = "";
            String info = request.getParameter("info");
//        info = "{\"userid\":21,\"amount\":\"199\",\"viptype\":\"4\"}";
            JSONObject joinfo = JSONObject.fromObject(info);
            vipBill vipBill = new vipBill(0, 0, "", joinfo.getInt("userid"), joinfo.getDouble("amount"), "", joinfo.getInt("viptype"), 0, "会员办理", "1900-01-01 00:00:00");
            String timeStamp = String.valueOf(new Date().getTime());
            vipBillService.insertInto(vipBill);
            user user = userService.selectByFuserid(joinfo.getString("userid"));
            wxPay wxPay = new wxPay("会员办理", "西鲜生会员", wxGetRandomStr(32), "/wxPayCallbackVip", vipBill.getFbillno(), String.valueOf((int) vipBill.getFamount() * 100), user.getFopenid());
            String prepayStr = wxPrepay(wxPay);
            JSONObject joPre = JSONObject.fromObject(prepayStr);
            if (joPre.get("result").equals("2")) {
                sign = getWXAPISign(wxPay.getAppid(), timeStamp, wxPay.getNonce_str(), "prepay_id=" + joPre.get("prepay_id"), "MD5", wxPay.getAppKey());
            }
            a = "{\"billno\":\"" + vipBill.getFbillno() + "\",\"timeStamp\":\"" + timeStamp + "\",\"nonceStr\":\"" + wxPay.getNonce_str() + "\",\"package\":\"" + "prepay_id=" + joPre.get("prepay_id") + "\",\"signType\":\"MD5\",\"paySign\":\"" + sign + "\"}";
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            errorLog errorLog = new errorLog(0, sdf.format(date), "", vipBill.getFbillno(), "wxPayVip", a);
            errorLogService.add(errorLog);
        } catch (Exception e) {
            publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxPayAmount")
    public @ResponseBody
    Map<String, Object> wxPayAmount(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        try {
            String sign = "";
            String info = request.getParameter("info");
            JSONObject recharge = JSONObject.fromObject(info);
            vipBill vipBill = new vipBill(0, 0, "", recharge.getInt("userid"), recharge.getDouble("price"), "", 0, recharge.getInt("rechargeType"), "会员充值", "1900-01-01 00:00:00");
            String timeStamp = String.valueOf(new Date().getTime());
            vipBillService.insertInto(vipBill);
            user user = userService.selectByFuserid(recharge.getString("userid"));
            wxPay wxPay = new wxPay("会员充值", "西鲜生会员", wxGetRandomStr(32), "/wxPayCallbackVip", vipBill.getFbillno(), String.valueOf((int) vipBill.getFamount() * 100), user.getFopenid());
            String prepayStr = wxPrepay(wxPay);
            JSONObject joPre = JSONObject.fromObject(prepayStr);
            if (joPre.get("result").equals("2")) {
                sign = getWXAPISign(wxPay.getAppid(), timeStamp, wxPay.getNonce_str(), "prepay_id=" + joPre.get("prepay_id"), "MD5", wxPay.getAppKey());
            }
            a = "{\"billno\":\"" + vipBill.getFbillno() + "\",\"timeStamp\":\"" + timeStamp + "\",\"nonceStr\":\"" + wxPay.getNonce_str() + "\",\"package\":\"" + "prepay_id=" + joPre.get("prepay_id") + "\",\"signType\":\"MD5\",\"paySign\":\"" + sign + "\"}";
        } catch (Exception e) {
            publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxPayBillTest")
    public @ResponseBody
    Map<String, Object> wxPayBillTest(HttpServletRequest request, HttpServletResponse response) {

        wxPay wxPay = new wxPay("测试支付", "JSAPI支付", wxGetRandomStr(32), "/wxPayCallback", request.getParameter("billno"), "1", "oyfri5Nt5-Rluk79l0gqgtrxLsoM");
        String a = wxPrepay(wxPay);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    private String wxPrepay(wxPay wxPay) {
//        bill bill = billService.selectByFbillno(billno);
//        user u = userService.selectByFuserid(String.valueOf(bill.getFuserID()));
        String signResult = "";
        String prepayid = "";
        String result = "0";
        String httpResult = "";
        String returnMsg = "";

        String sign = "";

        String backreturn_code = "";
        String backresult_code = "";
        String backerreds = "";
        String paramXML = "";
        try {
            Date begindate = new Date();
            Date enddate = new Date(begindate.getTime() + 1 * 60 * 1000);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

            JSONObject jo = new JSONObject();
            jo.put("appid", wxPay.getAppid());
            jo.put("attach", wxPay.getAttach());
            jo.put("body", wxPay.getBody());
            jo.put("mch_id", wxPay.getMch_id());
            jo.put("nonce_str", wxPay.getNonce_str());
            jo.put("notify_url", wxPay.getNotify_url());
            jo.put("out_trade_no", wxPay.getOut_trade_no());
            jo.put("spbill_create_ip", wxPay.getSpbill_create_ip());
            jo.put("total_fee", wxPay.getTotal_fee());
            jo.put("trade_type", wxPay.getTrade_type());
//            jo.put("openid", u.getFopenid());
            jo.put("openid", wxPay.getOpenid());
            sign = getWXSign(jo, wxPay.getAppKey());
            paramXML = "<xml>" +
                    "<appid>" + wxPay.getAppid() + "</appid>" +
//                    "<attach>" + new String(wxPay.getAttach().getBytes("UTF-8")) + "</attach>" +
//                    "<body>" + new String(wxPay.getBody().getBytes("UTF-8")) + "</body>" +

                    "<attach>" + wxPay.getAttach() + "</attach>" +
                    "<body>" + wxPay.getBody() + "</body>" +
                    "<mch_id>" + wxPay.getMch_id() + "</mch_id>" +
                    "<nonce_str>" + wxPay.getNonce_str() + "</nonce_str>" +
                    "<notify_url>" + wxPay.getNotify_url() + "</notify_url>" +
                    "<out_trade_no>" + wxPay.getOut_trade_no() + "</out_trade_no>" +
                    "<spbill_create_ip>" + wxPay.getSpbill_create_ip() + "</spbill_create_ip>" +
                    "<total_fee>" + wxPay.getTotal_fee() + "</total_fee>" +
                    "<trade_type>" + wxPay.getTrade_type() + "</trade_type>" +
                    "<openid>" + wxPay.getOpenid() + "</openid>" +
                    "<sign>" + sign + "</sign>" +
                    "</xml>";
            httpResult = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", paramXML);
//        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            errorLog errorLog = new errorLog(0, sdf.format(date), "", wxPay.getAppid(), "wxPrepayTop", e.getMessage());

            publicTools.writeInText(this.getClass().getName() + "|" + Thread.currentThread().getStackTrace()[1].getMethodName(), df.format(new Date()) + "|" + e.getMessage());
            errorLogService.add(errorLog);
        }
        try {

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            publicTools.writeInText("wxPrepay", httpResult + ",time:" + sdf.format(date));
            Document doc = DocumentHelper.parseText(httpResult);
            Element rootElt = doc.getRootElement();
            Iterator iterreturnCode = rootElt.elementIterator("return_code");
            Iterator iterresultCode = rootElt.elementIterator("result_code");
            while (iterreturnCode.hasNext()) {
                Element element = (Element) iterreturnCode.next();
                backreturn_code = element.getText();
            }
            while (iterresultCode.hasNext()) {
                Element element = (Element) iterresultCode.next();
                backresult_code = element.getText();
            }
            if (backreturn_code.equals("SUCCESS")) {
                Iterator iterSign = rootElt.elementIterator("sign");
                while (iterSign.hasNext()) {
                    Element element = (Element) iterSign.next();
                    signResult = element.getText();
                }
                if (backresult_code.equals("SUCCESS")) {
                    Iterator iterPrepay_id = rootElt.elementIterator("prepay_id");
                    while (iterPrepay_id.hasNext()) {
                        Element element = (Element) iterPrepay_id.next();
                        prepayid = element.getText();
                    }
                    result = "2";
                } else {
                    Iterator iterErrorDes = rootElt.elementIterator("err_code_des");
                    while (iterresultCode.hasNext()) {
                        Element element = (Element) iterErrorDes.next();
                        backerreds = element.getText();
                    }
                    result = "1";
                }
            } else {
                Iterator iterReturnMsg = rootElt.elementIterator("return_msg");
                while (iterReturnMsg.hasNext()) {
                    Element element = (Element) iterReturnMsg.next();
                    returnMsg = element.getText();
                }
                result = "0";
            }
        } catch (DocumentException e) {

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            errorLog errorLog = new errorLog(0, sdf.format(date), "", wxPay.getAppid(), "wxPrepayBottom", e.getMessage());
            publicTools.writeInText(this.getClass().getName() + "|" + Thread.currentThread().getStackTrace()[1].getMethodName(), df.format(new Date()) + "|" + e.getMessage());
            errorLogService.add(errorLog);
        }
        if (result.equals("0")) {
            return "{\"result\":\"" + result + "\",\"returnMsg\":\"" + returnMsg + "\",\"sign\":\"" +
                    sign + "\",\"paramXML\":\"" + paramXML + "\",\"httpResult\":\"" + httpResult + "\"}";

        } else if (result.equals("1")) {
            return "{\"result\":\"" + result + "\",\"backresult_code\":\"" + backerreds + "\",\"sign\":\"" +
                    sign + "\",\"paramXML\":\"" + paramXML + "\",\"httpResult\":\"" + httpResult + "\"}";
        } else if (result.equals("2")) {
            return "{\"result\":\"" + result + "\",\"sign\":\"" + signResult + "\",\"prepay_id\":\"" + prepayid + "\"}";
        } else {
            return "未知错误";
        }
    }

    private String wxGetRandomStr(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private String getWXSign(JSONObject json, String appKey) {
        String sign = "";
        try {
            List<String> signList = new ArrayList<String>();
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                signList.add(key);
            }
            Collections.sort(signList);
            for (int i = 0; i < signList.size(); i++) {
                String key = signList.get(i);
                String value = json.optString(key);
                sign = sign + key + "=" + value + "&";
            }
            sign = sign + "key=" + appKey;
            sign = DigestUtils.md5Hex(sign).toUpperCase();

        } catch (Exception e) {
            publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
        }
        // sign = MD5.getMessageDigest(sign.getBytes()).toUpperCase();
        return sign;
    }

    private String getWXAPISign(String appid, String timeStamp, String nonceStr, String packeage, String signType, String appKey) {
        JSONObject jo = new JSONObject();
        jo.put("appId", appid);
        jo.put("timeStamp", timeStamp);
        jo.put("nonceStr", nonceStr);
        jo.put("package", packeage);
        jo.put("signType", signType);
        return getWXSign(jo, appKey);
    }

    private void writeIntoFile(String billno, String info) {
        try {
            FileWriter fw = new FileWriter("D://callback//" + billno + "-" + String.valueOf(new Date().getTime()) + ".txt");
            fw.write(info);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
    @RequestMapping("/test")
    public @ResponseBody
    Map<String, Object> test(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String httpResult = "";
        try {
            httpResult = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxf4fbc87bd9579ae3&secret=607a2e5cc87a7370cbf64fcb21666643", "");
            JSONObject jo = JSONObject.fromObject(httpResult);
            access_token = jo.getString("access_token");
//            if(access_token!=""){
//                time=Time.
//            }
            String info = "{\"touser\":\"oyfri5Nt5-Rluk79l0gqgtrxLsoM\",\"template_id\":\"sNLjR3SRdjjEdGLARXEkPEUXcZdhfXBvLC2Z3bsxBvM\",\"page\":\"products\",\"form_id\":\"\",\"data\":{\"keyword1\":{\"value\":\"200元\"},\"keyword2\":{\"value\":\"西鲜生生鲜超市\"},\"keyword3\":{\"value\":\"2018-01-01 00:00:00\"},\"keyword4\":{\"value\":\"支付商品\"}},\"emphasis_keyword\":\"keyword1.DATA\"}";

            httpResult = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + access_token, info);

        } catch (Exception e) {
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", httpResult);
        return map;
    }

    @RequestMapping("/test1")
    public @ResponseBody
    Map<String, Object> test1(HttpServletRequest request, HttpServletResponse response) {
        String uuid = "3aea284c4b5601fd";
//        String a = prientBill(uuid, "XXS1902140005");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", uuid);
        return map;
    }


}
