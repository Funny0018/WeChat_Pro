package com.controller;

import com.entity.baseconfig;
import com.entity.optionalCity;
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
public class wxBaseConfigController {

    @Resource(name = "baseConfigService")
    private com.service.baseConfigService baseConfigService;

    @RequestMapping("/wxGetBaseConfig")
    public @ResponseBody
    Map<String, Object> wxGetBaseConfig(HttpServletRequest request, HttpServletResponse response) {
        baseconfig baseconfig = baseConfigService.selectByNone();
        String a = "";
        for (optionalCity v : baseconfig.getFoptionalCity()) {
            a += v.getFcityname() + ",";
        }
        a = a.substring(0, a.length() - 1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("yunfei", baseconfig.getFyunfei());
        map.put("baoyou", baseconfig.getFbaoyou());
        map.put("pagesize", baseconfig.getFpagesize());
        map.put("orderpagesize", baseconfig.getForderpagesize());
        map.put("sharecount", baseconfig.getFsharecount());
        map.put("openshare", baseconfig.getFopenshare());
        map.put("opentejia", baseconfig.getFopentejia());
        map.put("reCharge", 1);
        return map;
    }

    //后台函数
    @RequestMapping("/back/updageBaseConfig")
    public @ResponseBody
    Map<String, Object> updageBaseConfig(HttpServletRequest request, HttpServletResponse response) {
        double yunfei = Double.parseDouble(request.getParameter("yunfei"));
        double baoyou = Double.parseDouble(request.getParameter("baoyou"));
        int pagesize = Integer.parseInt(request.getParameter("baoyou"));
        int orderpagesize = Integer.parseInt(request.getParameter("baoyou"));
        int sharecount = Integer.parseInt(request.getParameter("sharecount"));
        int openshare = Integer.parseInt(request.getParameter("openshare"));
        int opentejia = Integer.parseInt(request.getParameter("opentejia"));
        baseconfig baseconfig = new baseconfig(1, null, yunfei, baoyou, pagesize, orderpagesize, sharecount, openshare, opentejia);
        int result = baseConfigService.updateInfo(baseconfig);
        String a = "";
        if (result > 0) {
            a = "更新成功";
        } else {
            a = "更新失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("back/GetBaseConfig")
    public @ResponseBody
    Map<String, Object> getBaseConfig(HttpServletRequest request, HttpServletResponse response) {
        baseconfig baseconfig = baseConfigService.selectByNone();
        String a = "";
        for (optionalCity v : baseconfig.getFoptionalCity()) {
            a += v.getFcityname() + ",";
        }
        a = a.substring(0, a.length() - 1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        map.put("yunfei", baseconfig.getFyunfei());
        map.put("baoyou", baseconfig.getFbaoyou());
        map.put("pagesize", baseconfig.getFpagesize());
        map.put("orderpagesize", baseconfig.getForderpagesize());
        map.put("sharecount", baseconfig.getFsharecount());
        map.put("openshare", baseconfig.getFopenshare());
        map.put("opentejia", baseconfig.getFopentejia());
        return map;
    }
}
