package com.controller;

import com.entity.*;
import com.service.couponService;
import com.tools.publicTools;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.*;

@Controller
public class wxShareController {
    @Resource(name = "shareLeaderService")
    private com.service.shareLeaderService shareLeaderService;
    @Resource(name = "userService")
    private com.service.userService userService;
    @Resource(name = "couponService")
    private com.service.couponService couponService;
    @Resource(name = "userCouponService")
    private com.service.userCouponService userCouponService;
    @Resource(name = "awardChanceService")
    private com.service.awardChanceService awardChanceService;

    @Resource(name = "baseConfigService")
    private com.service.baseConfigService baseConfigService;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/wxGetShare")
    public @ResponseBody
    Map<String, Object> wxGetShare(HttpServletRequest request, HttpServletResponse response) {
        int userid = Integer.valueOf(request.getParameter("fuserid"));
        String a = "";
        int sharecount = baseConfigService.selectByNone().getFsharecount();
        shareLeader sl = shareLeaderService.selectByFuserid(userid, String.valueOf(userid));
        if (sl != null) {
            try {
                if (df.parse(sl.getFendtime()).getTime() <= (new Date()).getTime()) {
                    sl.setFstate(-1);
                    shareLeaderService.update(sl);
                    a = "";
                } else {

                    String timedif = publicTools.printDifference(new Date(), df.parse(sl.getFendtime()));
                    user user1 = userService.selectByFuserid(String.valueOf(sl.getFuserid()));
                    String[] fuserids = sl.getFjoinuserid() == null ? new String[]{} : sl.getFjoinuserid().split(",");
                    String userinfo = "";
                    int teamercount = 0;
                    for (String s : fuserids) {
                        if (!s.equals("")) {
                            user u = userService.selectByFuserid(s);
                            userinfo += "{\"userImage\":\"" + u.getFimageurl() + "\",\"nikename\":\"" + u.getFnikename() + "\"},";
                            teamercount++;
                        }
                    }
                    if (userinfo != "") {
                        userinfo = userinfo.substring(0, userinfo.length() - 1);
                    }
                    a = "{\"fid\":\"" + sl.getFid() + "\",\"timedif\":\"" + timedif + "\",\"userImage\":\"" + user1.getFimageurl() + "\",\"joininfo\":[" + userinfo + "],\"fendtime\":\"" + sl.getFendtime() + "\",\"fuserlose\":\"" + (sharecount - teamercount) + "\"}";
                }
            } catch (ParseException e) {

            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxCreateShare")
    public @ResponseBody
    Map<String, Object> wxCreateShare(HttpServletRequest request, HttpServletResponse response) {
        int userid = Integer.valueOf(request.getParameter("fuserid"));
        String a = "";

        int sharecount = baseConfigService.selectByNone().getFsharecount();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-");
        String time = df1.format(new Date()) + "%";
        shareLeader sl1 = shareLeaderService.selectByFuserid(userid, String.valueOf(userid));
        if (sl1 == null) {
            int count = shareLeaderService.selectCountByFuserid(userid, time);
            if (count < 3) {
                Date date = new Date();
                Calendar ca = Calendar.getInstance();
                ca.add(Calendar.DATE, 1);// num为增加的天数，可以改变的
                Date d = ca.getTime();
                String enddate = df.format(d);
                shareLeader sl = new shareLeader(0, df.format(new Date()), enddate, userid, "", 0, 0, 0, 0);
                int result = shareLeaderService.add(sl);
                user user1 = userService.selectByFuserid(String.valueOf(sl.getFuserid()));
                a = "{\"fid\":\"" + sl.getFid() + "\",\"timedif\":\"" + "24,00,00" + "\",\"userImage\":\"" + user1.getFimageurl() + "\",\"joininfo\":[],\"fendtime\":\"" + sl.getFendtime() + "\",\"fuserlose\":\"" + sharecount + "\"}";
            } else {
                a = "toMany";
            }
        } else {
            try {
                String userinfo = "";
                user user1 = userService.selectByFuserid(String.valueOf(sl1.getFuserid()));
                String timedif = publicTools.printDifference(new Date(), df.parse(sl1.getFendtime()));
                String[] fuserids = sl1.getFjoinuserid() == null ? new String[]{} : sl1.getFjoinuserid().split(",");
                fuserids = sl1.getFjoinuserid() == null ? new String[]{} : sl1.getFjoinuserid().split(",");
                int teamercount = 0;
                for (String s : fuserids) {
                    if (!s.equals("")) {
                        user u = userService.selectByFuserid(s);
                        userinfo += "{\"userImage\":\"" + u.getFimageurl() + "\",\"nikename\":\"" + u.getFnikename() + "\"},";
                        teamercount++;
                    }
                }
                if (userinfo != "") {
                    userinfo = userinfo.substring(0, userinfo.length() - 1);
                }
                a = "{\"fid\":\"" + sl1.getFid() + "\",\"timedif\":\"" + timedif + "\",\"userImage\":\"" + user1.getFimageurl() + "\",\"joininfo\":[" + userinfo + "],\"fendtime\":\"" + sl1.getFendtime() + "\",\"fuserlose\":\"" + (sharecount - teamercount) + "\"}";
            } catch (ParseException e) {

            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxJoinShare")
    public @ResponseBody
    Map<String, Object> wxJoinShare(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        int shareID = Integer.valueOf(request.getParameter("shareid"));
        int sharecount = baseConfigService.selectByNone().getFsharecount();
        String openid = request.getParameter("openid");
        if (openid != "undefined") {
            shareLeader sl = shareLeaderService.selectByFid(shareID);
            try {
                if (sl.getFstate() == 0) {
                    if (df.parse(sl.getFendtime()).getTime() <= (new Date()).getTime()) {
                        if (sl.getFstate() == 0) {
                            user user1 = userService.selectByFuserid(String.valueOf(sl.getFuserid()));
                            user user = userService.selectByFopenidForHave(openid);
                            if (user == null) {
                                user = new user();
                                user.setFopenid(openid);
                                user.setFunionid("");
                                user.setFmoney(0.00);
                                userService.insertIntoUser(user);
                                user = userService.selectByFopenidForHave(openid);
                            }
                            String fjoinuserid = sl.getFjoinuserid() == null ? "" : sl.getFjoinuserid();
                            String[] fuserids = sl.getFjoinuserid() == null ? new String[]{} : sl.getFjoinuserid().split(",");
                            if (!fjoinuserid.contains(String.valueOf(user.getFuserid())) && user.getFuserid() != sl.getFuserid()) {
                                if (fjoinuserid.equals("")) {
                                    sl.setFjoinuserid(String.valueOf(user.getFuserid()));
                                    String timedif = publicTools.printDifference(new Date(), df.parse(sl.getFendtime()));
                                    String userinfo = "";
                                    int teamercount = 0;
                                    fuserids = sl.getFjoinuserid() == null ? new String[]{} : sl.getFjoinuserid().split(",");
                                    for (String s : fuserids) {
                                        if (!s.equals("")) {
                                            user u = userService.selectByFuserid(s);
                                            userinfo += "{\"userImage\":\"" + u.getFimageurl() + "\",\"nikename\":\"" + u.getFnikename() + "\"},";
                                            teamercount++;
                                        }
                                    }
                                    if (userinfo != "") {
                                        userinfo = userinfo.substring(0, userinfo.length() - 1);
                                    }
                                    a = "{\"fid\":\"" + sl.getFid() + "\",\"timedif\":\"" + timedif + "\",\"userImage\":\"" + user1.getFimageurl() + "\",\"joininfo\":[" + userinfo + "],\"fendtime\":\"" + sl.getFendtime() + "\",\"fuserlose\":\"" + (sharecount - teamercount) + "\"}";
                                } else {
                                    if (fuserids.length < (sharecount - 1)) {
                                        sl.setFjoinuserid(sl.getFjoinuserid() + "," + String.valueOf(user.getFuserid()));
                                        String timedif = publicTools.printDifference(new Date(), df.parse(sl.getFendtime()));
                                        fuserids = sl.getFjoinuserid().split(",");
                                        String userinfo = "";
                                        int teamercount = 0;
                                        fuserids = sl.getFjoinuserid() == null ? new String[]{} : sl.getFjoinuserid().split(",");
                                        for (String s : fuserids) {
                                            if (!s.equals("")) {
                                                user u = userService.selectByFuserid(s);
                                                userinfo += "{\"userImage\":\"" + u.getFimageurl() + "\",\"nikename\":\"" + u.getFnikename() + "\"},";
                                                teamercount++;
                                            }
                                        }
                                        if (userinfo != "") {
                                            userinfo = userinfo.substring(0, userinfo.length() - 1);
                                        }
                                        a = "{\"fid\":\"" + sl.getFid() + "\",\"timedif\":\"" + timedif + "\",\"userImage\":\"" + user1.getFimageurl() + "\",\"joininfo\":[" + userinfo + "],\"fendtime\":\"" + sl.getFendtime() + "\",\"fuserlose\":\"" + (sharecount - teamercount) + "\"}";
//                        }

                                    } else {
                                        sl.setFjoinuserid(sl.getFjoinuserid() + "," + String.valueOf(user.getFuserid()));
                                        sl.setFendtime(df.format(new Date()));
                                        List<awardChance> awardChanceList = awardChanceService.selectByNone();
                                        coupon leaderc = new coupon();
                                        coupon sharerc = new coupon();
                                        for (awardChance ac : awardChanceList) {
                                            if (ac.getFid() == 1) {
                                                leaderc = couponService.selectById(ac.getFcouponID());
                                            } else if (ac.getFid() == 2) {
                                                sharerc = couponService.selectById(ac.getFcouponID());
                                            }
                                        }
                                        setShareAword(userService.selectByFuserid(String.valueOf(sl.getFuserid())), leaderc);
                                        setFalertInfo(userService.selectByFuserid(String.valueOf(sl.getFuserid())), "发起分享活动成功，恭喜您获得5元抵扣券！");
                                        ///参与者奖励
                                        for (String userid : sl.getFjoinuserid().split(",")) {
                                            setShareAword(userService.selectByFuserid(String.valueOf(userid)), sharerc);
                                            setFalertInfo(userService.selectByFuserid(String.valueOf(userid)), "参与分享活动成功，恭喜您获得5元抵扣券！");
                                        }
                                        sl.setFstate(2);
                                        a = "活动完成";
                                    }
                                }
                            } else {
                                String timedif = publicTools.printDifference(new Date(), df.parse(sl.getFendtime()));
                                fuserids = sl.getFjoinuserid().split(",");
                                String userinfo = "";
                                int teamercount = 0;
                                for (String s : fuserids) {
                                    if (!s.equals("")) {
                                        user u = userService.selectByFuserid(s);
                                        userinfo += "{\"userImage\":\"" + u.getFimageurl() + "\",\"nikename\":\"" + u.getFnikename() + "\"},";
                                        teamercount++;
                                    }
                                }
                                if (userinfo != "") {
                                    userinfo = userinfo.substring(0, userinfo.length() - 1);
                                }
                                a = "{\"fid\":\"" + sl.getFid() + "\",\"timedif\":\"" + timedif + "\",\"userImage\":\"" + user1.getFimageurl() + "\",\"joininfo\":[" + userinfo + "],\"fendtime\":\"" + sl.getFendtime() + "\",\"fuserlose\":\"" + (sharecount - teamercount) + "\"}";
//                        }

                            }


                        } else {
                            sl.setFstate(-1);
                            a = "活动已过期";
                        }
                    } else {
                        sl.setFstate(-1);
                        a = "活动已过期";
                    }
                } else if (sl.getFstate() == 2) {
                    a = "活动已结束";
                } else if (sl.getFstate() == -1) {
                    a = "活动已过期";
                }
            } catch (ParseException e) {
                a = "数据错误";
            }

            shareLeaderService.update(sl);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetShareRank")
    public @ResponseBody
    Map<String, Object> wxGetShareRank(HttpServletRequest request, HttpServletResponse response) {
        baseconfig bc = baseConfigService.selectByNone();
        List<String> topUsers = shareLeaderService.selectTop();
        String a = "[";
        for (String s : topUsers) {
            user u = userService.selectByFuserid(s);
            a += "{\"userImage\":\"" + u.getFimageurl() + "\",\"nikename\":\"" + u.getFnikename() + "\",\"fcount\":\"" + shareLeaderService.selectCountByUser(s) + "\"},";
        }
        if (!a.equals("[")) {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("sharecount", bc.getFsharecount());
        return map;
    }

    @RequestMapping("/wxGetAllShareRank")
    public @ResponseBody
    Map<String, Object> wxGetAllShareRank(HttpServletRequest request, HttpServletResponse response) {

        int userid = Integer.valueOf(request.getParameter("userid"));
        List<String> topUsers = shareLeaderService.selectTop100();
        int userRank = 0;
        int rank = 0;
        String a = "[";
        for (String s : topUsers) {
            rank = rank + 1;
            user u = userService.selectByFuserid(s);
            if (userid == u.getFuserid()) {
                userRank = rank;
            }
            a += "{\"userImage\":\"" + u.getFimageurl() + "\",\"nikename\":\"" + u.getFnikename() + "\",\"fcount\":\"" + shareLeaderService.selectCountByUser(s) + "\",\"rank\":\"" + rank + "\"},";
        }

        if (!a.equals("[")) {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        user u = userService.selectByFuserid(String.valueOf(userid));
        String b = "{\"userImage\":\"" + u.getFimageurl() + "\",\"nikename\":\"" + u.getFnikename() + "\",\"fcount\":\"" + shareLeaderService.selectCountByUser(String.valueOf(userid))
                + "\",\"rank\":\"" + (userRank == 0 ? "99+" : userRank) + "\"}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("userRank", b);
        return map;
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

    private int setFalertInfo(user user, String info) {
        user.setFalertinfo(info);
        int i = userService.updateUserInfo(user);
        return i;
    }
}
