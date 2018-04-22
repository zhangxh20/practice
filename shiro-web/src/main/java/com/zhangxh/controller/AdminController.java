package com.zhangxh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("admin")
@Controller
public class AdminController {

    @RequestMapping("index")
    public String index() {
        return "admin/index";
    }
}
