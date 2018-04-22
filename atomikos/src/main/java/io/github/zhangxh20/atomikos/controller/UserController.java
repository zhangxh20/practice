package io.github.zhangxh20.atomikos.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.zhangxh20.atomikos.service.GroupService;

@RestController
public class UserController {

    @Autowired
    private GroupService groupService;
    
    @RequestMapping("create")
    public Map<String,Object> create() throws Exception {
        try {
            groupService.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "success");
        return map;
    }
}
