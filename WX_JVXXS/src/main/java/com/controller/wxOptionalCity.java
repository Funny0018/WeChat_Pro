package com.controller;

import com.entity.optionalCity;
import com.entity.user;
import com.tools.HttpRequest;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class wxOptionalCity {
    @Resource(name = "optionalCityService")
    private com.service.optionalCityService optionalCityService;

    @RequestMapping("/wxGetOptionalCity")
    public @ResponseBody
    Map<String, Object> wxGetOptionalCity(HttpServletRequest request, HttpServletResponse response) {
        List<optionalCity> optionalCityList = optionalCityService.selectByNone();
        String a = "";
        for (optionalCity v : optionalCityList) {
            a += v.getFcityname() + ",";
        }
        a = a.substring(0, a.length() - 1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    //后台
    @RequestMapping("/back/getOptionalCity")
    public @ResponseBody
    Map<String, Object> getOptionalCity(HttpServletRequest request, HttpServletResponse response) {
        List<optionalCity> optionalCityList = optionalCityService.selectByNone();
        String a = "[";
        for (optionalCity v : optionalCityList) {
            a += "{\"fid\":\"" + v.getFid() + "\",\"fname\":\"" + v.getFcityname() + "\"},";
        }
        if (a != "[") {
            a = a.substring(0, a.length() - 1);
        }
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/deleteOptionalCity")
    public @ResponseBody
    Map<String, Object> deleteOptionalCity(HttpServletRequest request, HttpServletResponse response) {
        String info = request.getParameter("key");
        List<String> ids = new ArrayList<String>();
        for (String s : info.split("\\|")) {
            ids.add(s.split(",")[0]);
        }
        int result = optionalCityService.deleteIds(ids);
        String a = "";
        if (result > 0) {
            a = "删除成功";
        } else {
            a = "删除失败";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/updateOptionalCity")
    public @ResponseBody
    Map<String, Object> updateOptionalCity(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        optionalCity optionalCity = new optionalCity(id, name, 1);
        int result = optionalCityService.update(optionalCity);
        String a = "";
        if (result > 0) {
            if (id != 0) {
                a = "更新成功";
            } else {
                a = "添加成功";
            }
        } else {
            if (id != 0) {
                a = "更新失败";
            } else {
                a = "添加失败";
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }
}
