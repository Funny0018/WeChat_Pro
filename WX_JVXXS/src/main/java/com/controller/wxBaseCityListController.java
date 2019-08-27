package com.controller;

import com.entity.baseCityList;
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
public class wxBaseCityListController {
    @Resource(name = "baseCityListService")
    private com.service.baseCityListService baseCityListService;

    @RequestMapping("/wxGetLoctionByCityName")
    public @ResponseBody
    Map<String, Object> wxGetLoctionByCityName(HttpServletRequest request, HttpServletResponse response) {
        String fname = request.getParameter("fname");
//        String fnamea = java.net.URLDecoder.decode(fname, "utf-8");
        List<baseCityList> baseCityListList = baseCityListService.selectByFName(fname);
        String a = baseCityListList.get(0).getFlat() + "," + baseCityListList.get(0).getFlng();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }
}
