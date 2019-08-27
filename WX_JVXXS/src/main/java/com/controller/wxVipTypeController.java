package com.controller;

import com.entity.vipType;
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
public class wxVipTypeController {

    @Resource(name = "vipTypeService")
    private com.service.vipTypeService vipTypeService;

    @RequestMapping("/wxGetVipType")
    public @ResponseBody
    Map<String, Object> wxGetVipType(HttpServletRequest request, HttpServletResponse response) {
        List<vipType> vipTypes = vipTypeService.selectByNone();
        String a = "[";
        for (vipType v : vipTypes) {
            a += "{\"fid\":\"" + v.getFid() + "\",\"fname\":\"" + v.getFname() + "\",\"ffullname\":\"" + v.getFfullname() + "\",\"fdiscount\":\"" + v.getFdiscount() + "\",\"foldprice\":" + v.getFoldprice() + ",\"fprice\":" + v.getFprice() + "},";
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
