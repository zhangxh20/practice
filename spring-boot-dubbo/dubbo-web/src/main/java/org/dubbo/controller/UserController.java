package org.dubbo.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.dubbo.bean.JsonResponse;
import org.dubbo.request.UserReq;
import org.dubbo.response.UserResp;
import org.dubbo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @DubboReference
    private UserService userService;
    
    @RequestMapping("register")
    public JsonResponse register(UserReq user) {
        userService.register(user);
        return JsonResponse.success();
    }
    
    @RequestMapping("getUser")
    public JsonResponse getUser(int id) {
        UserResp user = userService.getUserById(id);
        JsonResponse resp = new JsonResponse();
        resp.setData(user);
        return resp;
    }
}
