package com.controller;

import com.entity.rechargeType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class wxRechargeTypeController {


    @Resource(name = "rechargeTypeService")
    private com.service.rechargeTypeService rechargeTypeService;


    @RequestMapping("/wxGetRechargeType")
    public @ResponseBody
    Map<String, Object> wxGetRechargeType(HttpServletRequest request, HttpServletResponse response) {
        List<rechargeType> rechargeTypeList = rechargeTypeService.selectByNone();
        rechargeType rechargeType=new rechargeType(0,0,"其他",0,1,"","","https://xxs.xixiansheng.cn/wxStatic/images/recharge/10000.png");
        rechargeTypeList.add(rechargeType);
        String a = "[";
        for (rechargeType r : rechargeTypeList) {
            a += r.toString() + ",";
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
