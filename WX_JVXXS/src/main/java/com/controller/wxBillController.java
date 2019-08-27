package com.controller;

import com.entity.*;
import com.tools.printHelper;
import com.tools.publicTools;
import javafx.scene.input.DataFormat;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.tools.publicTools.toFloat;

@Controller
public class wxBillController {

    @Resource(name = "userService")
    private com.service.userService userService;

    @Resource(name = "billService")
    private com.service.billService billService;

    @Resource(name = "productService")
    private com.service.productService productService;
    @Resource(name = "prientTaskService")
    private com.service.prientTaskService prientTaskService;
    @Resource(name = "productTypeService")
    private com.service.productTypeService productTypeService;
    @Resource(name = "prientdeviceService")
    private com.service.prientdeviceService prientdeviceService;

    @Resource(name = "userCouponService")
    private com.service.userCouponService userCouponService;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/wxSetBillState")
    public @ResponseBody
    Map<String, Object> wxSetBillArrive(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String fbillno = request.getParameter("billno");
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bill bill = billService.selectByBillSimple(fbillno);
        bill.setFstate(3);
        bill.setFarrivedate(df.format(day));
        int result = billService.updateState(bill);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetOrder")
    public @ResponseBody
    Map<String, Object> wxGetOrder(HttpServletRequest request, HttpServletResponse response) {

        String a = "[";
        int maxCount = 0;
        int maxPage = 0;
        try {
            int crruPage = Integer.parseInt(request.getParameter("crruPage"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            int fstate = Integer.parseInt(request.getParameter("fstate"));
            int userid = Integer.parseInt(request.getParameter("userid"));
            if (crruPage <= 0) {
                crruPage = 1;
            }
            if (pageSize <= 0) {
                pageSize = 5;
            }
            List<bill> billList = billService.selectByQueryPage(crruPage, pageSize, fstate, userid);

            for (bill b : billList) {
                String productinfo = "[";
                if (b.getBillProducts() != null) {
                    if (b.getBillProducts().size() <= 5) {
                        for (billProduct p : b.getBillProducts()) {
                            productinfo += "{\"fProductId\":\"" + p.getFproductID() + "\",\"count\":\"" + p.getFcount() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fName\":\"" + p.getfName() + "\"},";
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            productinfo += "{\"fProductId\":\"" + b.getBillProducts().get(i).getFproductID() + "\",\"count\":\"" + b.getBillProducts().get(i).getFcount() + "\",\"fImgUrl\":\"" + b.getBillProducts().get(i).getfImgUrl() + "\",\"fName\":\"" + b.getBillProducts().get(i).getfName() + "\"},";
                        }
                    }
                    if (!productinfo.equals("[")) {
                        productinfo = productinfo.substring(0, productinfo.length() - 1);
                    }
                }
                productinfo += "]";

                a += "{\"totalprice\":\"" + b.getFtotalAmount() + "\",\"dikouprice\":\"" + b.getFdikouAmount() + "\",\"hongbaoprice\":\"" + b.getFhongbaoAmount() + "\",\"yunfei\":\"" + b.getFyunfei() + "\",\"finalProductPrice\":\"" + b.getFproductAmount() + "\",\"finalPrice\":\"" + b.getFfinalAmount() + "\",\"userid\":\"" + b.getFuserID() + "\",\"fproducts\":" + productinfo + ",\"fbillno\":\"" + b.getFbillNo() + "\",\"fbilldate\":\"" + b.getFbilldate() + "\",\"fstate\":\"" + b.getFstate() + "\",\"fstatename\":\"" + publicTools.getStateName(b.getFstate()) + "\"},";
            }
            if (a != "[") {
                a = a.substring(0, a.length() - 1);
            }
            a += "]";
            maxCount = billService.selectCount(fstate, userid, "");
            maxPage = 0;
            if (maxCount % pageSize == 0) {
                maxPage = maxCount / pageSize;
            } else {
                maxPage = maxCount / pageSize + 1;
            }
        } catch (Exception e) {
            publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("maxCount", maxCount);
        map.put("maxPage", maxPage);
        return map;
    }

    @RequestMapping("/wxSaveBill")
    public @ResponseBody
    Map<String, Object> wxSaveBill(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String info = request.getParameter("data");
        JSONObject jo = JSONObject.fromObject(info);
        List<product> confirmProducts = new ArrayList<product>();

        for (Object j : jo.getJSONArray("products")) {
            product p = productService.selectByFid(Integer.parseInt(JSONObject.fromObject(j).getString("productId")));
            if (p.getFistejia() > 0) {
                if (p.getFleftcount() - Float.parseFloat(JSONObject.fromObject(j).getString("count")) < 0) {
                    a = "商品" + p.getfName() + "库存不足";
                }
            }
        }

        if (a.equals("")) {
            try {
//            info = "{\"deliveryAddressId\":\"17\",\"totalprice\":\"15.90\",\"dikouprice\":\"￥15.9\",\"dikouid\":\"11\",\"hongbaoprice\":\"无可用红包\",\"yunfei\":\"￥3\",\"finalProductPrice\":\"￥0.00\",\"fvipamount\":\"￥0.00\",\"finalPrice\":\"￥3.00\",\"userid\":\"21\",\"products\":[{\"productId\":\"142\",\"count\":\"1\"}],\"ftotalpricevip\":\"￥1.40\"}";
                List<billProduct> billProductList = new ArrayList<billProduct>();
                for (Object j : jo.getJSONArray("products")) {
                    billProduct b = new billProduct(0, Integer.parseInt(JSONObject.fromObject(j).getString("productId")), 0, "", "", "", "", 0, 0, 0, "", Float.parseFloat(JSONObject.fromObject(j).getString("count")));
                    billProductList.add(b);
                    product p = productService.selectByFid(Integer.parseInt(JSONObject.fromObject(j).getString("productId")));
                    if (p.getFistejia() > 0) {
                        if (p.getFleftcount() - Float.parseFloat(JSONObject.fromObject(j).getString("count")) >= 0) {
                            p.setFleftcount(p.getFleftcount() - Integer.parseInt(JSONObject.fromObject(j).getString("count")));
                            productService.updateByFid(p);
                        }
                    }
                }
                billDeliveryAddress address = new billDeliveryAddress(0, Integer.parseInt(jo.getString("deliveryAddressId")), 0, "", "", "", "", "");
                Date day = new Date();

                int couponID = 0;
                bill bill = new bill();
                if (info.contains("dikouid")) {
                    couponID = jo.getInt("dikouid");
                    bill = new bill(0, "", 0, billProductList, address, toFloat(jo.getString("totalprice")), couponID, toFloat(jo.getString("dikouprice")), toFloat(jo.getString("hongbaoprice")), toFloat(jo.getString("finalProductPrice")), toFloat(jo.getString("yunfei")), toFloat(jo.getString("fvipamount")), toFloat(jo.getString("finalPrice")), toFloat(jo.getString("ftotalpricevip")), Integer.parseInt(jo.getString("userid")), df.format(day), "1900-01-01 00:00:00", "1900-01-01 00:00:00", "1900-01-01 00:00:00", "");
                    if (couponID != 0) {
                        userCouponService.updateState(couponID, 2);
                    }
                } else {
                    bill = new bill(0, "", 0, billProductList, address, toFloat(jo.getString("totalprice")), 0, toFloat(jo.getString("dikouprice")), toFloat(jo.getString("hongbaoprice")), toFloat(jo.getString("finalProductPrice")), toFloat(jo.getString("yunfei")), toFloat(jo.getString("fvipamount")), toFloat(jo.getString("finalPrice")), toFloat(jo.getString("ftotalpricevip")), Integer.parseInt(jo.getString("userid")), df.format(day), "1900-01-01 00:00:00", "1900-01-01 00:00:00", "1900-01-01 00:00:00", "");

                }
                a = billService.add(bill);
            } catch (Exception e) {
                publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetBill")
    public @ResponseBody
    Map<String, Object> wxGetBill(HttpServletRequest request, HttpServletResponse response) {
        String billno = request.getParameter("billno");
        String a = "";
        try {
            bill b = billService.selectByFbillno(billno);
            String productinfo = "[";

            for (billProduct p : b.getBillProducts()) {
                productinfo += "{\"fProductId\":\"" + p.getFproductID() + "\",\"count\":\"" + p.getFcount() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\"},";
            }
            if (!productinfo.equals("[")) {
                productinfo = productinfo.substring(0, productinfo.length() - 1);
            }
            productinfo += "]";
            a = "{\"deliveryAddress\":{\"name\":\"" + b.getBillDeliveryAddress().getfName() + "  " + b.getBillDeliveryAddress().getfMobile() + "\",\"faddress\":\"" + b.getBillDeliveryAddress().getfCity() + b.getBillDeliveryAddress().getfAddress() + b.getBillDeliveryAddress().getfBuildNo() + "\"},\"totalprice\":\"" + b.getFtotalAmount() + "\",\"dikoutitle\":\"" + (b.getFdikouAmount() > 0 ? userCouponService.selectBuFid(b.getFdikouid()).getFname() : "抵扣券") +
                    "\",\"dikouprice\":\"" + b.getFdikouAmount() + "\",\"hongbaoprice\":\"" + b.getFhongbaoAmount() + "\",\"yunfei\":\"" + b.getFyunfei() + "\",\"finalProductPrice\":\"" + b.getFproductAmount() + "\",\"finalPrice\":\"" + b.getFfinalAmount() + "\",\"userid\":\"" + b.getFuserID() + "\",\"fproducts\":" + productinfo + ",\"fbillno\":\"" + b.getFbillNo() + "\",\"fbilldate\":\"" + b.getFbilldate() + "\",\"fstate\":\"" + b.getFstate() + "\",\"fvipamount\":" + b.getFvipamount() + "}";

        } catch (Exception e) {

            publicTools.writeInText(this.getClass().getName(), df.format(new Date()) + "|" + Thread.currentThread().getStackTrace()[1].getMethodName() + "|" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }


    //后台
    @RequestMapping("/back/deleteOrder")
    public @ResponseBody
    Map<String, Object> deleteOrder(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String ids = request.getParameter("ids");
        int result = billService.deleteByIds(ids);
        if (result > 0) {
            a = "删除成功";
        } else {
            a = "删除失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/getOrder")
    public @ResponseBody
    Map<String, Object> wxGetOrderForKey(HttpServletRequest request, HttpServletResponse response) {

        String a = "[";
        int crruPage = Integer.parseInt(request.getParameter("pageIndex"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int fstate = Integer.parseInt(request.getParameter("fstate"));
        String key = request.getParameter("key");
        if (crruPage <= 0) {
            crruPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        if (key != null && key != "") {
            key = "%" + key + "%";
        }
        List<bill> billList = billService.selectByQueryPageForKey(crruPage, pageSize, fstate, key);

        for (bill b : billList) {
            String productinfo = "[";
            if (b.getBillProducts() != null) {
                for (billProduct p : b.getBillProducts()) {
                    productinfo += "{\"fProductId\":\"" + p.getFproductID() + "\",\"count\":\"" + p.getFcount() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\"},";
                }

                if (productinfo.length() > 0) {
                    productinfo = productinfo.substring(0, productinfo.length() - 1);
                }
            }
            productinfo += "]";

            a += "{\"fbillid\":" + b.getFbillid() + ",\"totalprice\":\"" + b.getFtotalAmount() + "\",\"dikouprice\":\"" + b.getFdikouAmount() + "\",\"hongbaoprice\":\"" + b.getFhongbaoAmount() + "\",\"yunfei\":\"" + b.getFyunfei() + "\",\"finalProductPrice\":\"" + b.getFproductAmount() + "\",\"finalPrice\":\"" + b.getFfinalAmount() + "\",\"userid\":\"" + b.getFuserID() + "\",\"fproducts\":" + productinfo + ",\"fbillno\":\"" + b.getFbillNo() + "\",\"fbilldate\":\"" + b.getFbilldate() + "\",\"fstate\":\"" + b.getFstate() + "\",\"fstatename\":\"" + publicTools.getStateName(b.getFstate()) + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        int maxCount = billService.selectCount(fstate, 0, key);
        int maxPage = 0;
        if (maxCount % pageSize == 0) {
            maxPage = maxCount / pageSize;
        } else {
            maxPage = maxCount / pageSize + 1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("maxCount", maxCount);
        map.put("maxPage", maxPage);
        return map;
    }

    @RequestMapping("/back/getOrderDetail")
    public @ResponseBody
    Map<String, Object> getOrderDetail(HttpServletRequest request, HttpServletResponse response) {
        String billno = request.getParameter("billno");
        String a = "";
        bill b = billService.selectByFbillno(billno);
        String productinfo = "[";
        boolean isvip = b.getFvipamount() > 0;
        for (billProduct p : b.getBillProducts()) {
            productinfo += "{\"fProductId\":\"" + p.getFproductID() + "\",\"count\":\"" + p.getFcount() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fname\":\"" + p.getfName() + "\",\"fprice\":\"" + p.getfPrice() + "\",\"fvipprice\":\"" + p.getFvipprice() + "\",\"fdec\":\"" + p.getfDesc() + "\",\"amount\":\"" + (isvip ? p.getFvipprice() * p.getFcount() : p.getfPrice() * p.getFcount()) + "\"},";
        }

        if (productinfo.length() > 0) {
            productinfo = productinfo.substring(0, productinfo.length() - 1);
        }
        productinfo += "]";
        a = "{\"deliveryAddress\":{\"name\":\"" + b.getBillDeliveryAddress().getfName() + "  " + b.getBillDeliveryAddress().getfMobile() + "\",\"faddress\":\"" + b.getBillDeliveryAddress().getfCity() + b.getBillDeliveryAddress().getfAddress() + b.getBillDeliveryAddress().getfBuildNo() + "\"},\"totalprice\":\"" + b.getFtotalAmount() + "\",\"dikouprice\":\"" + b.getFdikouAmount() + "\",\"hongbaoprice\":\"" + b.getFhongbaoAmount() + "\",\"yunfei\":\"" + b.getFyunfei() + "\",\"finalProductPrice\":\"" + b.getFproductAmount() + "\",\"finalPrice\":\"" + b.getFfinalAmount() + "\",\"userid\":\"" + b.getFuserID() + "\",\"fproducts\":" + productinfo + ",\"fbillno\":\"" + b.getFbillNo() + "\",\"fbilldate\":\"" + b.getFbilldate() + "\",\"fstate\":\"" + b.getFstate() + "\",\"fvipamount\":" + b.getFvipamount() + ",\"fpaydate\":\"" + b.getFpaydate() + "\",\"fsenddate\":\"" + b.getFsenddate() + "\",\"farrivedate\":\"" + b.getFarrivedate() + "\",\"fsendno\":\"" + b.getFsendno() + "\"}";


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/sendOrder")
    public @ResponseBody
    Map<String, Object> sendOrder(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String fbillno = request.getParameter("billno");
        String sendno = request.getParameter("sendno");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        bill b = billService.selectByFbillno(fbillno);
        b.setFstate(2);
        b.setFsenddate(df.format(date));
        b.setFsendno(sendno);
        int result = billService.updateState(b);
        if (result > 0) {
            a = "保存成功";
        } else {
            a = "保存成功";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/getPrientDevice")
    public @ResponseBody
    Map<String, Object> getPrientDevice(HttpServletRequest request, HttpServletResponse response) {
        String a = "[";
        List<prientdevice> list = prientdeviceService.selectByNone();
        for (prientdevice p : list) {
            a += "{\"fid\":\"" + p.getFid() + "\",\"fuuid\":\"" + p.getFuuid() + "\",\"fname\":\"" + p.getFname() + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/prientBill")
    public @ResponseBody
    Map<String, Object> prientBill(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String fbillno = request.getParameter("billno");
        String prientID = request.getParameter("prientID");
        prientdevice pd = prientdeviceService.selectByFID(prientID);
        a = prientBill(pd, fbillno);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    private String prientBill(prientdevice pd, String billno) {
        String result = "";
        String resultBind = "";
        String resultPrient = "";
        String resultState = "";
        prientTask pt = new prientTask();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pt.setFbillno(billno);
        pt.setFprientdate(df.format(new Date()));
        resultBind = printHelper.userBind(pd.getFuuid(), String.valueOf(pd.getFid()), pd.getFname());//100 您系统的用户编号（自己定义）最好是数字
        if (resultBind != "" && JSONObject.fromObject(resultBind).getString("Code").equals("200")) {
            JSONObject jo = JSONObject.fromObject(resultBind);
            resultState = printHelper.getDeviceState(pd.getFuuid());

            if (resultState != "" && JSONObject.fromObject(resultState).getString("Code").equals("200")) {
                if (JSONObject.fromObject(resultState).getInt("State") == 0) {
                    bill bill = billService.selectByFbillno(billno);
                    user u = userService.selectByFuserid(String.valueOf(bill.getFuserID()));
                    String jsonContent = publicTools.getPrientBill(bill, (u.getFisvip() == 1));
                    resultPrient = printHelper.printContent(pd.getFuuid(), jsonContent, jo.getString("OpenUserId"));//改成用户设备绑定返回的OpenUserId
                    if (resultPrient != "" && JSONObject.fromObject(resultPrient).getString("Code").equals("200")) {
                        result = "打印成功";
                    } else {
                        if (resultPrient == "") {
                            result = "打印失败，未知原因";
                        } else {
                            result = "打印失败," + JSONObject.fromObject(resultPrient).getString("Message");
                        }
                    }
                } else {
                    switch (JSONObject.fromObject(resultState).getInt("State")) {
                        case -1:
                            result = "打印机异常状态：错误";
                            break;
                        case 1:
                            result = "打印机异常状态：缺纸";
                            break;
                        case 2:
                            result = "打印机异常状态：温度保护警告";
                            break;
                        case 3:
                            result = "打印机异常状态：忙碌";
                            break;
                        case 4:
                            result = "打印机异常状态：离线";
                            break;
                        default:
                            result = "打印机异常状态：错误状态值";
                            break;
                    }
                }
            } else {
                if (resultState == "") {
                    result = "获取状态失败，未知原因";
                } else {
                    result = "获取状态失败," + JSONObject.fromObject(resultState).getString("Message");
                }
            }
        } else {
            if (resultBind == "") {
                result = "绑定失败，未知原因";
            } else {
                result = "绑定失败，" + JSONObject.fromObject(resultBind).getString("Message");
            }
        }
        if (resultBind != "") {
            pt.setFbindstate(JSONObject.fromObject(resultBind).getString("Code"));
            pt.setFbindmassage(JSONObject.fromObject(resultBind).getString("Message"));
            if (resultState != "") {
                pt.setFstatestate(JSONObject.fromObject(resultState).getString("Code"));
                pt.setFstatemassage(JSONObject.fromObject(resultState).getString("Message"));
                if (resultPrient != "") {
                    pt.setFprientstate(JSONObject.fromObject(resultPrient).getString("Code"));
                    pt.setFprientmassage(JSONObject.fromObject(resultPrient).getString("Message"));
                    pt.setFtaskno(JSONObject.fromObject(resultPrient).getString("TaskId"));
                }
            }
        }
        prientTaskService.add(pt);
        return result;
    }


}
