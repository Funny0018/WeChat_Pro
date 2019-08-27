package com.controller;

import com.entity.prientdevice;
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
public class wxBasePrintController {

    @Resource(name = "prientdeviceService")
    private com.service.prientdeviceService prientdeviceService;

    @RequestMapping("/back/updatePrientDevice")
    public @ResponseBody
    Map<String, Object> updatePrientDevice(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String uuid = request.getParameter("uuid");
        prientdevice prientdevice = new prientdevice(id, uuid, name);
        int result = prientdeviceService.update(prientdevice);
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

    @RequestMapping("/back/deletePrientDevice")
    public @ResponseBody
    Map<String, Object> deletePrientDevice(HttpServletRequest request, HttpServletResponse response) {
        String info = request.getParameter("key");
        List<String> ids = new ArrayList<String>();
        for (String s : info.split("\\|")) {
            ids.add(s.split(",")[0]);
        }
        int result = prientdeviceService.deleteIds(ids);
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

}
