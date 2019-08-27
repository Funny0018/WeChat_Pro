package com.controller;

import com.entity.baseconfig;
import com.entity.producttype;
import com.service.baseConfigService;
import com.service.productTypeService;
import net.sf.json.JSONObject;
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
public class wxProductTypeController {
    @Resource(name = "productTypeService")
    private productTypeService productTypeService;

    @Resource(name = "baseConfigService")
    private baseConfigService baseConfigService;

    @RequestMapping("/wxGetProductType")
    public @ResponseBody
    Map<String, Object> wxGetProductType(HttpServletRequest request, HttpServletResponse response) {
        List<producttype> productList = productTypeService.selectByNone();
        baseconfig bs = baseConfigService.selectByNone();
        String a = "[{\"ftypeid\":0,\"fName\":\"全部\",\"fstate\":1,\"fchildid\":\"\"},";
        if (bs.getFopentejia() > 0) {
            a += "{\"ftypeid\":99999,\"fName\":\"会员特价\",\"fstate\":1,\"fchildid\":\"\"},";
        }
        for (producttype p : productList) {
            a += "{\"ftypeid\":" + p.getFtypeid() + ",\"level\":\"1\",\"fName\":\"" + p.getFname() + "\",\"fstate\":\"" + p.getFstate() + "\",\"fchildid\":[";
            List<producttype> listchild = productTypeService.selectByParentId(p.getFtypeid());
            if (listchild.size() > 0 && listchild != null) {
                a += "{\"ftypeid\":\"" + p.getFtypeid() + "\",\"level\":\"2\",\"fparnetid\":\"" + p.getFtypeid() + "\",\"fName\":\"全部\",\"fstate\":1,\"fchildid\":\"\"},";
                for (producttype pt : listchild) {
                    a += "{\"ftypeid\":" + pt.getFtypeid() + ",\"level\":\"2\",\"fparnetid\":\"" + p.getFtypeid() + "\",\"fName\":\"" + pt.getFname() + "\",\"fstate\":\"" + pt.getFstate() + "\"},";
                }
                a = a.substring(0, a.length() - 1);
                a += "],\"fhaschild\":\"true\"";
            } else {
                a += "],\"fhaschild\":\"false\"";
            }
            a += "},";
        }
        a = a.substring(0, a.length() - 1);
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    ///后台
    @RequestMapping("/back/getAllProductType")
    public @ResponseBody
    Map<String, Object> getAllProductType(HttpServletRequest request, HttpServletResponse response) {
        List<producttype> productList = productTypeService.selectByNoneAll();
        String a = "[";
        for (producttype p : productList) {
            a += "{\"ftypeid\":" + p.getFtypeid() + ",\"fName\":\"" + p.getFname() + "\",\"fstate\":\"" + p.getFstate() + "\",\"fparentname\":\"" + (p.getFparentid() == 0 ? "无父级分类" : productTypeService.selectById(p.getFparentid()).getFname()) + "\"},";
        }
        a = a.substring(0, a.length() - 1);
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }


    @RequestMapping("/back/getParentType")
    public @ResponseBody
    Map<String, Object> getParentType(HttpServletRequest request, HttpServletResponse response) {
        int typeid = Integer.valueOf(request.getParameter("typeid"));
        List<producttype> productList = productTypeService.selectByParentId(typeid);
        String a = "[";
        for (producttype p : productList) {
            a += "{\"ftypeid\":" + p.getFtypeid() + ",\"fName\":\"" + p.getFname() + "\",\"fstate\":\"" + p.getFstate() + "\"},";
        }
        a = a.substring(0, a.length() - 1);
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/getProductType")
    public @ResponseBody
    Map<String, Object> getProductType(HttpServletRequest request, HttpServletResponse response) {
        List<producttype> productList = productTypeService.selectByNone();
        String a = "[";
        for (producttype p : productList) {
            a += "{\"ftypeid\":" + p.getFtypeid() + ",\"fName\":\"" + p.getFname() + "\",\"fstate\":\"" + p.getFstate() + "\"},";
        }
        a = a.substring(0, a.length() - 1);
        a += "]";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/getProductTypeById")
    public @ResponseBody
    Map<String, Object> getProductTypeById(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        int fid = Integer.parseInt(request.getParameter("fid"));
        producttype productList = productTypeService.selectById(fid);
        a += "{\"ftypeid\":" + productList.getFtypeid() + ",\"fName\":\"" + productList.getFname() + "\",\"fstate\":\"" + productList.getFstate() + "\"}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/updateProductTypeById")
    public @ResponseBody
    Map<String, Object> updateProductTypeById(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        int fid = Integer.parseInt(request.getParameter("fid"));
        String fname = request.getParameter("fname");
        int fparenttype = Integer.valueOf(request.getParameter("fparentid"));
        int result = productTypeService.updateFname(fname, fid, fparenttype);
        if (result > 0) {
            a = "更新成功";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/deleteProductTypeById")
    public @ResponseBody
    Map<String, Object> deleteProductTypeById(HttpServletRequest request, HttpServletResponse response) {
        String a = "删除成功";
        String fids = request.getParameter("fid");
        for (String fid : fids.split("\\|")) {
            if (fid != "") {
                int result = productTypeService.deleteById(Integer.parseInt(fid));
                if (result < 1) {
                    a = "删除失败";
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/back/addProductTypeById")
    public @ResponseBody
    Map<String, Object> addProductTypeById(HttpServletRequest request, HttpServletResponse response) {
        String a = "";
        String fname = request.getParameter("fname");
        int fparenttype = Integer.valueOf(request.getParameter("fparentid"));
//        JSONObject jo = JSONObject.fromObject(info);
        producttype producttype = new producttype(0, fname, 1, fparenttype);
        productTypeService.add(producttype);
        if (producttype.getFtypeid() > 0) {
            a = "添加成功";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }
}
