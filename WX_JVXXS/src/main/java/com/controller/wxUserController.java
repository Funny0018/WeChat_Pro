package com.controller;

import com.entity.*;
import com.service.baseConfigService;
import com.tools.HttpRequest;
import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class wxUserController {
    //51ee851379e5ba25a4eadea099825d2d


    @Resource(name = "userService")
    private com.service.userService userService;
    @Resource(name = "vipBillService")
    private com.service.vipBillService billVipService;
    @Resource(name = "billService")
    private com.service.billService billService;
    @Resource(name = "userCouponService")
    private com.service.userCouponService userCouponService;
    @Resource(name = "couponService")
    private com.service.couponService couponService;
    @Resource(name = "baseConfigService")
    private com.service.baseConfigService baseConfigService;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/wxLogin")
    public @ResponseBody
    Map<String, Object> wxLogin(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String userInfo = request.getParameter("userInfo");

//        code="071L7dQB1HBdCd083WOB17UWPB1L7dQy";
//        code="081gfyof2x0QrH0h2aqf22Yrof2gfyoy";
        String a = "";
        String param = "appid=wxc6adc19848efee7b&secret=3b2728929d7b3a2414c77c4fda15122a&js_code=" + code + "&grant_type=authorization_code";
        a = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", param);
        JSONObject jo = JSONObject.fromObject(a);

        user user = userService.selectByFopenid(String.valueOf(jo.getString("openid")));

        if (!userInfo.equals("null") && userInfo != "undefine") {
            updateUserInfo(user, userInfo);
        }
        int istehui = billVipService.selectVipIsTehui(user.getFuserid());
        int price = billService.selectVipPriceForUser(user.getFuserid());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int days = 0;
        try {
            days = (int) ((sdf.parse(user.getFvipenddate()).getTime() - (new Date()).getTime()) / (1000 * 3600 * 24));
        } catch (ParseException e) {

        }
        if (days < 0) {
            days = 0;
        }
        baseconfig baseconfig = baseConfigService.selectByNone();
        for (optionalCity v : baseconfig.getFoptionalCity()) {
            a += v.getFcityname() + ",";
        }
        a = a.substring(0, a.length() - 1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("optionalcity", a);
        map.put("yunfei", baseconfig.getFyunfei());
        map.put("baoyou", baseconfig.getFbaoyou());
        map.put("pagesize", baseconfig.getFpagesize());
        map.put("orderpagesize", baseconfig.getForderpagesize());
        map.put("sharecount",baseconfig.getFsharecount());
        map.put("openshare",baseconfig.getFopenshare());
        map.put("opentejia",baseconfig.getFopentejia());
        map.put("massage", user.toString());
        map.put("tehui", istehui);
        map.put("jiesheng", price);
        map.put("days", days);
        map.put("reCharge", 1);
        return map;
    }

    @RequestMapping("/wxUpdateUserInfo")
    public @ResponseBody
    Map<String, Object> wxUpdateUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String fuserid = request.getParameter("fuserid");
        String userInfo = request.getParameter("userInfo");
        if (!userInfo.equals("null") && userInfo != "undefine") {
            user user = userService.selectByFuserid(fuserid);

            updateUserInfo(user, userInfo);
            int istehui = billVipService.selectVipIsTehui(user.getFuserid());
            int price = billService.selectVipPriceForUser(user.getFuserid());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int days = 0;
            try {
                days = (int) ((sdf.parse(user.getFvipenddate()).getTime() - (new Date()).getTime()) / (1000 * 3600 * 24));
            } catch (ParseException e) {

            }
            if (days < 0) {
                days = 0;
            }
            baseconfig baseconfig = baseConfigService.selectByNone();
            String a="";
            for (optionalCity v : baseconfig.getFoptionalCity()) {
                a += v.getFcityname() + ",";
            }
            a = a.substring(0, a.length() - 1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("optionalcity", a);
            map.put("yunfei", baseconfig.getFyunfei());
            map.put("baoyou", baseconfig.getFbaoyou());
            map.put("pagesize", baseconfig.getFpagesize());
            map.put("orderpagesize", baseconfig.getForderpagesize());
            map.put("sharecount",baseconfig.getFsharecount());
            map.put("openshare",baseconfig.getFopenshare());
            map.put("opentejia",baseconfig.getFopentejia());
            map.put("massage", user.toString());
            map.put("tehui", istehui);
            map.put("jiesheng", price);
            map.put("days", days);
            map.put("reCharge", 1);
            return map;
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("massage", "");
            return map;
        }
    }

    @RequestMapping("/back/getUserInfo")
    public @ResponseBody
    Map<String, Object> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        int crruPage = Integer.parseInt(request.getParameter("pageIndex"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int type = Integer.parseInt(request.getParameter("type"));
        int userid = Integer.parseInt((request.getParameter("userid").equals("") ? "0" : request.getParameter("userid")));
        List<user> user = userService.selectBack(userid, crruPage, pageSize, type);
        int maxCount = userService.selectCountBack(type);
        int maxPage = 0;
        if (maxCount % pageSize == 0) {
            maxPage = maxCount / pageSize;
        } else {
            maxPage = maxCount / pageSize + 1;
        }
        String a = "[";
        for (user u : user) {
            a += "{\"fuserid\":" + u.getFuserid() + ",\"fopenid\":\"" + u.getFopenid() + "\",\"funionid\":\"" + u.getFunionid() + "\",\"fmoney\":" + u.getFmoney() + ",\"fisvip\":\"" + u.getFisvip() + "\",\"fstart\":\"" + u.getFvipstartdate() + "\",\"fend\":\"" + u.getFvipenddate() + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("maxCount", maxCount);
        map.put("maxPage", maxPage);
        return map;
    }

    @RequestMapping("/getNewCoupon")
    public @ResponseBody
    Map<String, Object> getNewCoupon(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("userid");
        String result = "";
        int isNew = userCouponService.selectIsNew(userid, "新人红包");
        if (isNew == 0) {
            user user = userService.selectByFuserid(userid);
            user.setFnewcoupon(2);
            userService.updateUserInfo(user);
            coupon coupon = couponService.selectById(8);//新人红包，固定ID
            setShareAword(user, coupon);
            result = "领取新人红包成功！该红包无法抵扣购买会员！";
        } else {
            user user = userService.selectByFuserid(userid);
            user.setFnewcoupon(2);
            userService.updateUserInfo(user);
            result = "您已领取过新人红包。";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", result);
        return map;
    }

    @RequestMapping("/setAlertInfo")
    public @ResponseBody
    Map<String, Object> setAlertInfo(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("userid");
        user user = userService.selectByFuserid(userid);
        user.setFalertinfo("");
        userService.updateUserInfo(user);
        Map<String, Object> map = new HashMap<String, Object>();
        return map;
    }

    private void updateUserInfo(user user, String userInfo) {
        if (!userInfo.equals("null") && userInfo != "undefine" && user != null) {
            try {
                JSONObject joinfo = JSONObject.fromObject(userInfo);
                if (!(user.getFimageurl().equals(joinfo.getString("avatarUrl")) && user.getFnikename().equals(joinfo.getString("nickName")))) {
                    user.setFimageurl(joinfo.getString("avatarUrl"));
                    user.setFnikename(joinfo.getString("nickName"));
                    userService.updateUserInfo(user);
                }
            } catch (JSONException e) {
            }

        }
    }

    private int setShareAword(user user, coupon c) {
        Date date = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.YEAR, Integer.parseInt(c.getFoverdate().split(",")[0]));//要增加什么，在这里写
        nowTime.add(Calendar.MONTH, Integer.parseInt(c.getFoverdate().split(",")[1]));//要增加什么，在这里写
        nowTime.add(Calendar.DATE, Integer.parseInt(c.getFoverdate().split(",")[2]));//要增加什么，在这里写
        userCoupon userCoupon = new userCoupon(0, user.getFuserid(), c.getFname(), c.getFtype(), c.getFproductId(), c.getFprice(), c.getFminprice(), df.format(date), df.format(nowTime.getTime()), 1);
        int rusult = userCouponService.add(userCoupon);

        return rusult;
    }


}
