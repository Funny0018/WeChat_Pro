package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("/notFound")
    public String goNotFound() {
        return "backView/notFound";
    }
    @RequestMapping("/backLogin")
    public String goLogin() {
        return "backLogin";
    }

    @RequestMapping("/back/index")
    public String goIndex(HttpServletResponse response, HttpSession session) {
////        session.setAttribute("userinfo", backuser);
        boolean islogin = session.getAttribute("login") == null ? false : (boolean) session.getAttribute("login");
        if (islogin) {
            return "backView/index";
        } else {
            return "redirect:/ backLogin ";
        }
    }

    @RequestMapping("/back/indexIframe")
    public String goIndexIfream() {
        return "backView/indexIframe";
    }

    ///商品
    @RequestMapping("/back/editProduct")
    public String goEditProduct() {
        return "backView/product/editProduct";
    }

    @RequestMapping("/back/listProduct")
    public String goListProduct() {
        return "backView/product/listProduct";
    }
    @RequestMapping("/back/editProductTejia")
    public String goEditProductTejia() {
        return "backView/product/editProductTejia";
    }

    @RequestMapping("/back/listProductTejia")
    public String goListProductTejia() {
        return "backView/product/listProductTejia";
    }

    @RequestMapping("/back/editProductType")
    public String goEditProductType() {
        return "backView/product/editProductType";
    }

    @RequestMapping("/back/listProductType")
    public String goListProductType() {
        return "backView/product/listProductType";
    }


    @RequestMapping("/back/listProductTag")
    public String goListProductTag() {
        return "backView/product/listProductTag";
    }

    @RequestMapping("/back/editProductTag")
    public String goEitProductTag() {
        return "backView/product/editProductTag";
    }

    ///订单列表
    @RequestMapping("/back/listOrder")
    public String goListOrder() {
        return "backView/order/listOrder";
    }
    @RequestMapping("/back/orderdetail")
    public String goOrderDetail() {
        return "backView/order/detailOrder";
    }

    ///会员
    @RequestMapping("/back/listVip")
    public String goListVip() {
        return "backView/vip/listvip";
    }
    @RequestMapping("/back/listcoupon")
    public String golistcoupon() {
        return "backView/vip/listcoupon";
    }
    @RequestMapping("/back/listprize")
    public String golisrprize() {
        return "backView/vip/listprize";
    }

    ///基础设置
    @RequestMapping("/back/baseconfig")
    public String goBaseConfig() {
        return "backView/base/baseconfig";
    }

    @RequestMapping("/back/optioncity")
    public String goOptionCity() {
        return "backView/base/optioncity";
    }

    @RequestMapping("/back/prientDevice")
    public String goprientDevice() {
        return "backView/base/prientDevice";
    }

}
