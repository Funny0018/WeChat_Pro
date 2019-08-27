package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class wxIndexController {

    @RequestMapping("/wxProduct")
    public String goNotFound() {
        return "appPages/product";
    }
}
