package com.veyit.waterstoragesystem.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.veyit.waterstoragesystem.entity.vo.ForgetPwd;
import com.veyit.waterstoragesystem.entity.vo.Pwd;
import com.veyit.waterstoragesystem.mapper.UserMapper;
import com.veyit.waterstoragesystem.result.Result;
import com.veyit.waterstoragesystem.entity.po.User;
import com.veyit.waterstoragesystem.tools.FilterInput;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private LoginService loginService;

    /**
    * @Description: 更新用户名,性别,邮箱
    */
    public Result<User> update(User user) {
        if (user.getPhone() == null) {
            return new Result<>(2000,"电话号码不能为空！");
        }
        if(!FilterInput.verifyMatch(FilterInput.PHONE_RULES,user.getPhone())){
            return new Result<>(2000,"电话号码格式不正确！");
        }
        int count = userMapper.update(user);
        if(count!=0){
            return new Result<>(200,"用户信息更新成功！");
        }else{
            return new Result<>(2003,"更新失败！");
        }
    }

    public Result<User> updateUser(Integer id,User user) {
        User u = userMapper.getUserById(id);
        if (u.getRole()==0){
            if (user.getPhone() == null) {
                return new Result<>(2000,"电话号码不能为空！");
            }
            if(!FilterInput.verifyMatch(FilterInput.PHONE_RULES,user.getPhone())){
                return new Result<>(2000,"电话号码格式不正确！");
            }
            int count = userMapper.updateUser(user);
            if(count!=0){
                return new Result<>(200,"用户信息更新成功！");
            }else{
                return new Result<>(2003,"更新失败！");
            }
        }
        return new Result<>(2001,"无管理员权限！");
    }

    public Result<User> delete(Integer role,Integer uid,Integer id) {
        if (role==0){
            if (uid==id) {
                return new Result<>(2000,"不能删除当前登录用户!");
            }
            User user = userMapper.getUserById(uid);
            if(user != null) {
                userMapper.deleteUserById(uid);
                return new Result<>(200,"删除账号为："+user.getId()+"成功!");
            }
            return new Result<>(2002,"用户不存在！");
        } else  {
            return new Result<>(2001,"无管理员权限！");
        }
    }

    public Result<PageInfo<User>> getUserList(Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        return new Result<>(200, "获取成功！", new PageInfo<>(Optional
                .ofNullable(userMapper.getUserList())
                .orElse(Collections.emptyList())));
    }

    public Result<User> getUserById(int id) {
        User u = userMapper.getUserById(id);
        if(u!=null){
            u.setPassword(null);
            return new Result<>(200,"获取用户信息成功！",u);
        }
        return new Result<>(2002,"用户不存在");
    }

    public Result updatePwd(Integer id, Pwd pwd) {
        User user = userMapper.getUserById(id);
        if(BCrypt.checkpw(pwd.getPassword(),user.getPassword())){
            if(!FilterInput.verifyMatch(FilterInput.PASSWORD_RULES, pwd.getNewPwd())){
                return new Result<>(2000,"新密码不符合格式");
            }
            if(BCrypt.checkpw(pwd.getNewPwd(),user.getPassword())){
                return new Result<>(2000,"新密码不可以和旧密码一样");
            }
            String n = BCrypt.hashpw(pwd.getNewPwd(), BCrypt.gensalt());
            int count = userMapper.updateUserPwd(n,user.getId());
            if(count==1){
                return new Result<>(200,"更新成功");
            }else{
                return new Result<>(2003,"更新失败！");
            }
        }else{
            return new Result<>(2002,"密码错误");
        }
    }

    public Result<User> getUById(Integer id) {
        User u = userMapper.getUById(id);
        if(u!=null){
            u.setPassword(null);
            return new Result<>(200,"获取用户信息成功！",u);
        }
        return new Result<>(2002,"用户不存在");
    }

    public Result forgetPwd(Integer id, ForgetPwd forgetPwd) {
        User user = userMapper.getUserById(id);
        if(!FilterInput.verifyMatch(FilterInput.PHONE_RULES,forgetPwd.getPhone())){
            return new Result<>(2000,"电话号码格式不正确！");
        }
        if (!user.getPhone().equals(forgetPwd.getPhone())) {
            return new Result<>(2000,"电话号码不匹配！");
        }
        if(!FilterInput.verifyMatch(FilterInput.PASSWORD_RULES, forgetPwd.getNewPwd())){
            return new Result<>(2000,"新密码不符合格式");
        }
        String n = BCrypt.hashpw(forgetPwd.getNewPwd(), BCrypt.gensalt());
        int count = userMapper.updateUserPwd(n,user.getId());
        if(count==1){
            return new Result<>(200,"更新成功");
        }else{
            return new Result<>(2003,"更新失败！");
        }
    }

    public Result<User> add(Integer id,User user) {
        User u = userMapper.getUserById(id);
        if (u.getRole()==0){
            if (user.getPhone().equals("")){
                return new Result<>(2000,"电话号码不能为空！");
            }
            if(!FilterInput.verifyMatch(FilterInput.PHONE_RULES,user.getPhone())){
                return new Result<>(2000,"电话号码格式不正确！");
            }
            String n = BCrypt.hashpw(user.getPhone().substring(5), BCrypt.gensalt());
            user.setPassword(n);
            int count = userMapper.insertUser(user);
            if (count==1){
                return new Result<>(200,"添加用户成功！");
            }
            return new Result<>(2003,"添加用户失败！");
        }
        return new Result<>(2001,"无管理员权限！");
    }
}
