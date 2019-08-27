package com.controller;

import com.entity.coupon;
import com.entity.product;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.tools.publicTools.getCouponTypeName;

@Controller
public class wxCouponController {

    @Resource(name = "couponService")
    private com.service.couponService couponService;
    @Resource(name = "productService")
    private com.service.productService productService;

    @RequestMapping("/back/getProductFname")
    public @ResponseBody
    Map<String, Object> getProductFname(HttpServletRequest request, HttpServletResponse response) {
        String a = "[";
        String fname = request.getParameter("fname");
        List<product> list = productService.selectByKeyAndType(1, 100000, 0, "%" + fname + "%", -1, -1);
        for (product p : list) {
            a += "{\"fProductId\":" + p.getfProductId() + ",\"ftype\":" + p.getFtype() + ",\"fName\":\"" + p.getfName() + "\",\"fImgUrl\":\"" + p.getfImgUrl() + "\",\"fDesc\":\"" + p.getfDesc() + "\",\"fDescDetail\":\"" + p.getfDescDetail() + "\",\"fOldPrice\":" + p.getfOldPrice() + ", \"fPrice\":" + p.getfPrice() + ",\"fUnit\":\"" + p.getfUnit() + "\",\"count\":\"0\",\"fVipPrice\":" + p.getFvipprice() + ",\"ftoptag\":\"" + p.getFtoptag() + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/getCoupon")
    public @ResponseBody
    Map<String, Object> getCoupon(HttpServletRequest request, HttpServletResponse response) {
        String a = "[";
        String fstate = request.getParameter("fstate");
        String fname = request.getParameter("fname");
        String ftype = request.getParameter("ftype");
        List<coupon> couponList = couponService.selectByQueryForBack(Integer.valueOf(fstate), fname, ftype);
        for (coupon c : couponList) {
            a += "{\"fid\":\"" + c.getFid() + "\",\"fname\":\"" + c.getFname() + "\",\"ftype\":\"" + c.getFtype() + "\",\"fproductID\":\"" + c.getFproductId() + "\",\"fproductName\":\"" + (c.getFproductId() > 0 ? productService.selectByFid(c.getFproductId()).getfName() : "0") + "\",\"fprice\":\"" + c.getFprice() + "\",\"fstartdate\":\"" + c.getFstartdate() + "\",\"fenddate\":\"" + c.getFenddate() + "\",\"fstate\":\"" + c.getFstate() + "\",\"foverdate\":\"" + c.getFoverdate() + "\",\"fminprice\":\"" + c.getFminprice() + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/updateCoupon")
    public @ResponseBody
    Map<String, Object> updateCoupon(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String info = request.getParameter("info");
        JSONObject jo = JSONObject.fromObject(info);
        coupon c = new coupon(jo.getInt("fid"), jo.getString("fname"), jo.getInt("ftype"), jo.getString("fproductid").trim().equals("") ? 0 : jo.getInt("fproductid"), jo.getString("fprice").trim().equals("") ? 0.00 : jo.getDouble("fprice"), jo.getString("fminprice").equals("") ? 0 : jo.getDouble("fminprice"), jo.getString("fstartdate"), jo.getString("fenddate"), jo.getString("foverdate"), jo.getInt("fstate"));
        a = couponService.update(c) > 0 ? "保存成功" : "保存失败";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/setCouponState")
    public @ResponseBody
    Map<String, Object> setCouponState(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String fstate = request.getParameter("fstate");
        String fids = request.getParameter("fids");
        List<String> list = new ArrayList<String>();
        String[] fid = fids.split("\\|");
        for (String s : fid) {
            list.add(s);
        }
        a = couponService.updateState(fstate, list) > 0 ? "修改成功" : "修改失败";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/getCouponByKey")
    public @ResponseBody
    Map<String, Object> getCouponByKey(HttpServletRequest request, HttpServletResponse response) {
        String a = "[";
        String key = request.getParameter("key");
        List<coupon> couponList = couponService.selectByQueryForBack(1, key, "0");
        String type = "";
        for (coupon c : couponList) {
            type = getCouponTypeName(c.getFtype());
            a += "{\"fid\":\"" + c.getFid() + "\",\"fname\":\"" + c.getFname() + "\",\"ftype\":\"" + type + "\",\"fproductID\":\"" + c.getFproductId() + "\",\"fproductName\":\"" + (c.getFproductId() > 0 ? productService.selectByFid(c.getFproductId()).getfName() : "0") + "\",\"fprice\":\"" + c.getFprice() + "\",\"fstartdate\":\"" + c.getFstartdate() + "\",\"fenddate\":\"" + c.getFenddate() + "\",\"fstate\":\"" + c.getFstate() + "\",\"foverdate\":\"" + c.getFoverdate() + "\",\"fminprice\":\"" + c.getFminprice() + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }
}
