package com.controller;

import com.entity.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class wxPrizeChanceController {
    @Resource(name = "awardChanceService")
    private com.service.awardChanceService awardChanceService;
    @Resource(name = "couponService")
    private com.service.couponService couponService;
    @Resource(name = "productService")
    private com.service.productService productService;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/wxGetAwardChanceInfo")
    public @ResponseBody
    Map<String, Object> wxGetAwardChanceInfo(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        List<awardChance> list = awardChanceService.selectByNone();
        List<awardChanceFroCoupon> forCouponList = new ArrayList();
        for (awardChance a : list) {
            awardChanceFroCoupon b = new awardChanceFroCoupon(a.getFid(), a.getFcouponID(), a.getFchance(), couponService.selectById(a.getFcouponID()));
            forCouponList.add(b);
        }
        result = "[";
        double sumChance = 0;
        String typeName = "";
        boolean faultCoupon = false;
        for (awardChanceFroCoupon a : forCouponList) {
            switch (a.getFcoupon().getFtype()) {
                case 1:
                    typeName = "金额抵扣";
                    break;
                case 2:
                    typeName = "商品抵扣";
                    break;
                default:
                    faultCoupon = true;
                    break;

            }
            String fproductName = "";
            if (typeName.equals("商品抵扣")) {
                product p = productService.selectByFid(a.getFcoupon().getFproductId());
                fproductName = p.getfName();
                if (p.getFstate() <= 0) {
                    faultCoupon = true;
                }
            }
            try {

                if (df.parse(a.getFcoupon().getFenddate()).getTime() < (new Date()).getTime()) {
                    faultCoupon = true;
                }
            } catch (ParseException e) {
                e.getMessage();
            }
            sumChance += faultCoupon ? 0 : a.getFchance();
            result += "{\"fcouponID\":\"" + a.getFcoupon().getFid() + "\",\"fname\":\"" + a.getFcoupon().getFname() + "\",\"ftype\":\"" + typeName + "\",\"fproductID\":\"" + fproductName + "\",\"fprice\":\"" + (int) a.getFcoupon().getFprice() + "\",\"fchance\":\"" + sumChance + "\",\"fminprice\":\"" + (int) a.getFcoupon().getFminprice() + "\"},";
            typeName = "";
        }
        if (result != "[") {
            result = result.substring(0, result.length() - 1);
        }
        result += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", result);
        return map;
    }


    @RequestMapping("/back/getAwardChance")
    public @ResponseBody
    Map<String, Object> getAwardChance(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        List<awardChance> list = awardChanceService.selectByNone();
        List<awardChanceFroCoupon> forCouponList = new ArrayList();
        List<awardChance> newList = new ArrayList<awardChance>() {
        };
        for (int i = 0; i <2; i++) {
            newList.add(list.get(i));
        }
        for (awardChance a : newList) {
            awardChanceFroCoupon b = new awardChanceFroCoupon(a.getFid(), a.getFcouponID(), a.getFchance(), couponService.selectById(a.getFcouponID()));
            forCouponList.add(b);
        }
        result = "[";
        String typeName = "";
        boolean faultCoupon = false;
        for (awardChanceFroCoupon a : forCouponList) {
            switch (a.getFcoupon().getFtype()) {
                case 1:
                    typeName = "金额抵扣";
                    break;
                case 2:
                    typeName = "商品抵扣";
                    break;
                default:
                    faultCoupon = true;
                    break;

            }
            String fproductName = "";
            if (typeName.equals("商品抵扣")) {
                product p = productService.selectByFid(a.getFcoupon().getFproductId());
                fproductName = p.getfName();
            }

            result += "{\"fchanceID\":\"" + a.getFid() + "\",\"fcouponID\":\"" + a.getFcoupon().getFid() + "\",\"fname\":\"" + a.getFcoupon().getFname() + "\",\"ftype\":\"" + typeName + "\",\"fproductID\":\"" + fproductName + "\",\"fprice\":\"" + (int) a.getFcoupon().getFprice() + "\",\"fchance\":\"" + a.getFchance() + "\",\"fminprice\":\"" + (int) a.getFcoupon().getFminprice() + "\"},";
            typeName = "";
        }
        if (result != "[") {
            result = result.substring(0, result.length() - 1);
        }
        result += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", result);
        return map;
    }

    @RequestMapping("/back/updateAwardChance")
    public @ResponseBody
    Map<String, Object> updateAwardChance(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        int fid = Integer.valueOf(request.getParameter("fid"));
        int fcoupID = Integer.valueOf(request.getParameter("fcouponID"));
//        double chance = Double.valueOf(request.getParameter("fchance"));
        awardChance a = new awardChance(fid, fcoupID, 0);
        int update = awardChanceService.update(a);
        result = update > 0 ? "修改成功" : "修改失败";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", result);
        return map;
    }
}
