package com.controller;

import com.entity.deliveryAddress;
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
public class wxDeliveryAddressController {
    @Resource(name = "deliveryAddressService")
    private com.service.deliveryAddressService deliveryAddressService;

    @RequestMapping("/wxAddDeliveryAddress")
    public @ResponseBody
    Map<String, Object> wxAddDeliveryAddress(HttpServletRequest request, HttpServletResponse response) {
        deliveryAddress deliveryAddress = new deliveryAddress();
        deliveryAddress.setFid(Integer.parseInt(request.getParameter("id")));
        deliveryAddress.setFname(request.getParameter("name"));
        deliveryAddress.setFphone(request.getParameter("phone"));
        deliveryAddress.setFcity(request.getParameter("city"));
        deliveryAddress.setFaddress(request.getParameter("address"));
        deliveryAddress.setFbuildno(request.getParameter("buildno"));
        deliveryAddress.setFisdefault(0);
        deliveryAddress.setFuserid(Integer.parseInt(request.getParameter("userid")));

        deliveryAddressService.add(deliveryAddress);

        String a = String.valueOf(deliveryAddress.getFid());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetDeliveryAddress")
    public @ResponseBody
    Map<String, Object> wxGetDeliveryAddress(HttpServletRequest request, HttpServletResponse response) {

        int userid = Integer.parseInt(request.getParameter("userid"));
        List<deliveryAddress> deliveryAddressList = deliveryAddressService.selectByFuserid(userid);
        String a = "";
        if (deliveryAddressList.size() > 0) {
            a = "[";
            for (deliveryAddress d : deliveryAddressList) {
                a += "{\"fid\":\"" + d.getFid() + "\",\"fname\":\"" + d.getFname() + "\",\"fphone\":\"" + d.getFphone() + "\",\"fcity\":\"" + d.getFcity() + "\",\"faddress\":\"" + d.getFaddress() + "\",\"fbuildno\":\"" + d.getFbuildno() + "\"},";
            }
            a = a.substring(0, a.length() - 1);
            a += "]";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxGetDeliveryAddressByFid")
    public @ResponseBody
    Map<String, Object> wxGetDeliveryAddressByFid(HttpServletRequest request, HttpServletResponse response) {

        int fid = Integer.parseInt(request.getParameter("fid"));
        deliveryAddress deliveryAddressList = deliveryAddressService.selectByFid(fid);
        String a = "";
        a += "{\"fid\":\"" + deliveryAddressList.getFid() + "\",\"fname\":\"" + deliveryAddressList.getFname() + "\",\"fphone\":\"" + deliveryAddressList.getFphone() + "\",\"fcity\":\"" + deliveryAddressList.getFcity() + "\",\"faddress\":\"" + deliveryAddressList.getFaddress() + "\",\"fbuildno\":\"" + deliveryAddressList.getFbuildno() + "\"}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }

    @RequestMapping("/wxDeleteDeliveryAddressByFid")
    public @ResponseBody
    Map<String, Object> wxDeleteDeliveryAddressByFid(HttpServletRequest request, HttpServletResponse response) {

        int fid = Integer.parseInt(request.getParameter("fid"));
        int result = deliveryAddressService.delete(fid);
        String a = String.valueOf(result);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("massage", a);
        return map;
    }
}
