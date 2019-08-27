package com.controller;

import com.entity.producttag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.producttagService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class wxProductTagController {
    @Resource(name = "producttagService")
    private producttagService producttagService;

    ///后台
    @RequestMapping("/back/getProductTag")
    public @ResponseBody
    Map<String, Object> getProductTag(HttpServletRequest request, HttpServletResponse response) {
        List<producttag> productList = producttagService.selectByNone();
        String a = "[";
        for (producttag p : productList) {
            a += "{\"ftypeid\":" + p.getFid() + ",\"fName\":\"" + p.getFname() + "\",\"fstate\":\"" + p.getFstate() + "\"},";
        }
        a = a.substring(0, a.length() - 1);
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }


    @RequestMapping("/back/getProductTagById")
    public @ResponseBody
    Map<String, Object> getProductTagById(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        int fid = Integer.parseInt(request.getParameter("fid"));
        producttag productList = producttagService.selectById(fid);
        a += "{\"ftypeid\":" + productList.getFid() + ",\"fName\":\"" + productList.getFname() + "\",\"fstate\":\"" + productList.getFstate() + "\"}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/updateProductTagById")
    public @ResponseBody
    Map<String, Object> updateProductTagById(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        int fid = Integer.parseInt(request.getParameter("fid"));
        String fname = request.getParameter("fname");
        int result = producttagService.updateFname(fname, fid);
        if (result > 0) {
            a = "更新成功";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/deleteProductTagById")
    public @ResponseBody
    Map<String, Object> deleteProductTagById(HttpServletRequest request, HttpServletResponse response) {
        String a = "删除成功";
        String fids = request.getParameter("fid");
        for (String fid : fids.split("\\|")) {
            if (fid != "") {
                int result = producttagService.deleteById(Integer.parseInt(fid));
                if (result < 1) {
                    a = "删除失败";
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/addProductTagById")
    public @ResponseBody
    Map<String, Object> addProductTagById(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String fname = request.getParameter("fname");
//        JSONObject jo = JSONObject.fromObject(info);
        producttag producttype = new producttag(0, fname, 1);
        producttagService.add(producttype);
        if (producttype.getFid() > 0) {
            a = "添加成功";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }
}
