package com.veyit.waterstoragesystem.controller;

import com.github.pagehelper.PageInfo;
import com.veyit.waterstoragesystem.entity.po.User;
import com.veyit.waterstoragesystem.entity.vo.ForgetPwd;
import com.veyit.waterstoragesystem.entity.vo.Pwd;
import com.veyit.waterstoragesystem.result.Result;
import com.veyit.waterstoragesystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    //添加用户
    @PostMapping("add/{id}")
    public Result<User> add(@PathVariable Integer id,@RequestBody User user) {
        return userService.add(id,user);
    }

    //路径参数
    @PutMapping(value = "update",consumes = "application/json")
    public Result<User> update(@RequestBody User user) {
        // TODO 登录否 - 改的这个用户 或者 管理员（只要登录了就可以）
        return userService.update(user);
    }

    @PutMapping(value = "updateUser/{id}")
    public Result<User> updateUser(@PathVariable Integer id,@RequestBody User user) {
        // TODO 登录否 - 改的这个用户 或者 管理员（只要登录了就可以）
        return userService.updateUser(id,user);
    }
    //路径参数
    @DeleteMapping(value = "delete")
    public Result<User> delete(@RequestParam Integer role,@RequestParam Integer uId, @RequestParam Integer id) {
        // TODO 管理员
        return userService.delete(role,uId,id);
    }

    //   获取用户列表(管理)
    @PostMapping(value = "users")
    public Result<PageInfo<User>> getUserList(@RequestParam Integer page,@RequestParam Integer limit){
        return userService.getUserList(page,limit);
    }
    //   通过userid获取用户名,性别,邮箱
    @GetMapping(value = "userInfo/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        return userService.getUserById(id);
    }

    //   通过userid获取用户名,性别,邮箱
    @GetMapping(value = "uInfo/{id}")
    public Result<User> getUById(@PathVariable("id") Integer id){
        return userService.getUById(id);
    }

    //路径参数
    @PutMapping(value = "update/pwd/{id}")
    public Result updatePwd(@PathVariable Integer id, @RequestBody Pwd pwd){
        return userService.updatePwd(id, pwd);
    }

    //忘记密码
    @PutMapping(value = "forget/pwd/{id}")
    public Result forgetPwd(@PathVariable Integer id, @RequestBody ForgetPwd forgetPwd){
        return userService.forgetPwd(id, forgetPwd);
    }
}
