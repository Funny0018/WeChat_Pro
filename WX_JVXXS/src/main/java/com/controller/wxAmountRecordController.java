package com.controller;

import com.entity.amountRecord;
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
public class wxAmountRecordController {
    @Resource(name = "amountRecordService")
    private com.service.amountRecordService amountRecordService;

    @RequestMapping("/wxGetAmountRecord")
    public @ResponseBody
    Map<String, Object> wxGetAmountRecord(HttpServletRequest request, HttpServletResponse response) {
        int crruPage = Integer.parseInt(request.getParameter("pageIndex"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String type = request.getParameter("type");
        int userid = Integer.parseInt(request.getParameter("userid"));
        List<Integer> types = new ArrayList<Integer>();
        for (String i : type.split(",")) {
            types.add(Integer.valueOf(i));
        }
        List<amountRecord> amountRecordList = amountRecordService.selectByUserAndType(crruPage, pageSize, types, userid);
        int maxCount = amountRecordService.selectCountByUserAndType(types, userid);
        int maxPage = 0;
        if (maxCount % pageSize == 0) {
            maxPage = maxCount / pageSize;
        } else {
            maxPage = maxCount / pageSize + 1;
        }
        String a = "[";
        for (amountRecord ar : amountRecordList) {
            a += "{\"fdate\":\"" + ar.getFdate() + "\",\"fpayamount\":\"" + ar.getFpayamount() + "\",\"famount\":\"" + ar.getFamount() + "\",\"fbillno\":\"" + ar.getFbillno() + "\",\"wxno\":\"" + ar.getFwxno() + "\",\"fattech\":\"" + ar.getFattech() + "\",\"fbilltype\":\"" + ar.getFbilltype() + "\"},";
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
}
