package com.controller;

import com.entity.coupon;
import com.entity.product;
import com.entity.user;
import com.entity.userCoupon;
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
public class wxUserCouponController {
    @Resource(name = "userCouponService")
    private com.service.userCouponService userCouponService;
    @Resource(name = "userService")
    private com.service.userService userService;
    @Resource(name = "productService")
    private com.service.productService productService;

    @Resource(name = "couponService")
    private com.service.couponService couponService;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/wxSetAward")
    public @ResponseBody
    Map<String, Object> wxSetAward(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        String info = request.getParameter("info");
        JSONObject jo = JSONObject.fromObject(info);
        int fuserid = jo.getInt("fuserid");
        int fcouponid = jo.getInt("faward");
        user user = userService.selectByFuserid(String.valueOf(fuserid));
        user.setFawardtime(user.getFawardtime() - 1);
        userService.updateUserInfo(user);
        coupon c = couponService.selectById(fcouponid);
        Date date = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.YEAR, Integer.parseInt(c.getFoverdate().split(",")[0]));//要增加什么，在这里写
        nowTime.add(Calendar.MONTH, Integer.parseInt(c.getFoverdate().split(",")[1]));//要增加什么，在这里写
        nowTime.add(Calendar.DATE, Integer.parseInt(c.getFoverdate().split(",")[2]));//要增加什么，在这里写
        userCoupon userCoupon = new userCoupon(0, fuserid, c.getFname(), c.getFtype(), c.getFproductId(), c.getFprice(), c.getFminprice(), df.format(date), df.format(nowTime.getTime()), 1);
        userCouponService.add(userCoupon);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", result);
        return map;
    }

    @RequestMapping("/wxGetUserCoupon")
    public @ResponseBody
    Map<String, Object> wxGetUserCoupon(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        int fuserid = Integer.parseInt(request.getParameter("fuserid"));
        List<userCoupon> list = userCouponService.selectByFuserid(fuserid);
        result = "[";
//        private int fid;
//        private int fuserid;
//        private String fname;
//        private int ftype;
//        private int fproductId;
//        private double fprice;
//        private double fminprice;
//        private String fstartdate;
//        private String fenddate;
//        // -1：停用 0.未启用 1：正常 2:已使用 3：过期
//        private int fstate;
        String type = "";
        String productName = "";
        String productImg = "";
        double productPrice = 0;
        double productVipPrice = 0;

        for (userCoupon c : list) {
            productName = "";
            if (c.getFtype() == 2) {
                product p = productService.selectByFid(c.getFproductId());
                productName = p.getfName();
                productImg = p.getfImgUrl();
                productPrice = p.getfPrice();
                productVipPrice = p.getFvipprice();
            }
            type = getCouponTypeName(c.getFtype());
            result += "{\"fid\":\"" + c.getFid() + "\",\"fname\":\"" + c.getFname() + "\",\"ftype\":\"" + type + "\",\"fproductId\":\"" + c.getFproductId() + "\",\"fproductName\":\"" + productName +
                    "\",\"productImg\":\"" + productImg + "\",\"fprice\":\"" + (int) c.getFprice() + "\",\"fminprice\":\"" + (int) c.getFminprice() + "\",\"fstartdate\":\"" + c.getFstartdate().split(" ")[0] + "\",\"fenddate\":\"" + c.getFenddate().split(" ")[0] + "\",\"fstate\":\"" + c.getFstate() + "\",\"fproductPrice\":\"" + productPrice + "\",\"fproductVipPrice\":\"" + productVipPrice + "\",\"canUse\":\"1\",\"useInfo\":\"\"},";
        }
        if (result != "[") {
            result = result.substring(0, result.length() - 1);
        }
        result += "]";
        int count = userCouponService.selectCountByFuserid(fuserid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", result);
        map.put("count", count);
        return map;
    }

}
