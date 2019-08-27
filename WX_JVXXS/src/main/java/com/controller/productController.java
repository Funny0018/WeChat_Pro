package com.controller;

import com.entity.product;
import com.entity.producttype;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.service.productService;
import com.service.productTypeService;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class productController {
    @Resource(name = "productService")
    private productService productService;
    @Resource(name = "productTypeService")
    private productTypeService productTypeService;



}
