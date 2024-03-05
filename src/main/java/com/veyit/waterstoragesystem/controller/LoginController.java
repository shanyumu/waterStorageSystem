package com.veyit.waterstoragesystem.controller;

import com.veyit.waterstoragesystem.entity.vo.UserVO;
import com.veyit.waterstoragesystem.result.Result;
import com.veyit.waterstoragesystem.entity.po.User;
import com.veyit.waterstoragesystem.service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@CrossOrigin
public class LoginController {
    @Resource
    private LoginService loginService;

    // TODO 根据用户名登录 用户名/密码 限制(英文/数字/特殊符号)

    //路径参数
    @GetMapping(value = "userInfo")
    public Result<User> info(@RequestHeader("Authorization") String token) {
        return loginService.info(token);
    }

    //路径参数
    @PostMapping(value = "register",consumes = "application/json")
    public Result<User> register(@RequestBody User user) {
        return loginService.register(user);
    }

    //路径参数
    @PostMapping(value = "login")
    public Result login(@RequestBody UserVO userVO) {
        return loginService.login(userVO.getId(), userVO.getPassword());
    }

    //路径参数
    @PostMapping(value = "logout")
    public Result login(@RequestHeader("Authorization") String token) {
        return loginService.logout(token);
    }

}
