package com.controller;

import com.entity.backuser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class backUserController {

    @Resource(name = "backUserService")
    private com.service.backUserService backUserService;

    @RequestMapping("/back/login")
    public @ResponseBody
    Map<String, Object> wxLogin(HttpServletRequest request, HttpServletResponse response) {
        int aa = 0;
        String name = request.getParameter("name");
        String password = request.getParameter("psd");
        backuser backuser = backUserService.selectByNameAndPsd(new backuser(0, name, password, "", 0));
        String a = "";
        if (backuser != null && backuser.getFid() > 0) {
            a = "登录成功";
            HttpSession session = request.getSession(true);
            session.setAttribute("login", true);
            session.setAttribute("userinfo", backuser);
        } else {
            a = "登录失败";
            HttpSession session = request.getSession(true);
            session.setAttribute("login", false);
            session.setAttribute("userinfo", null);

        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }



}
