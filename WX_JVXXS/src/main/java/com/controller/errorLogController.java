package com.controller;

import com.entity.amountRecord;
import com.entity.errorLog;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class errorLogController {
    @Resource(name = "errorLogService")
    private com.service.errorLogService errorLogService;

    @RequestMapping("/wxSaveErrorLog")
    public @ResponseBody
    Map<String, Object> wxGetAmountRecord(HttpServletRequest request, HttpServletResponse response) {
        String info = request.getParameter("info");
        info = "{\"fphonemodel\":\"Nexus 6\",\"fuserid\":\"21\",\"fflag\":\"pay\",\"ferrormsg\":\"requestPayment:fail parameter error: parameter.package should be String instead of Undefined;\"}";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject o = JSONObject.fromObject(info);
        errorLog errorLog = new errorLog(0, sdf.format(date), o.getString("fphonemodel"), o.getString("fuserid"), o.getString("fflag"), o.getString("ferrormsg"));
        int result = errorLogService.add(errorLog);
        String a = "";
        if (result > 0) {
            a = "保存成功";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }
}
