package com.veyit.waterstoragesystem.service;

import com.alibaba.fastjson.JSON;
import com.veyit.waterstoragesystem.mapper.UserMapper;
import com.veyit.waterstoragesystem.result.Result;
import com.veyit.waterstoragesystem.entity.po.User;
import com.veyit.waterstoragesystem.tools.FilterInput;
import com.veyit.waterstoragesystem.utils.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public Result<User> info(String token) {
        User user = checkToken(token);
        if(user == null) {
            return new Result<>(3002,"用户未登录");
        }
        System.out.println("User"+user);
        return new Result<>(200,"获取信息成功！",user);
    }

    public Result login(Integer id,String password) {
        User u = userMapper.getUserById(id);
        if(u != null){
            if(BCrypt.checkpw(password, u.getPassword())){
                //登录成功，使用JWT生成token，返回token和redis中
                String token = JwtUtils.createToken(u.getId());
                u.setPassword(null);
                redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(u),1, TimeUnit.DAYS);
                return new Result(200,"登陆成功！",token);
            }
        }
        return new Result<>(2002,"用户不存在或用户密码错误！");
    }

    public User checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JwtUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        //解析为User对象
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        User user = JSON.parseObject(userJson,User.class);
        return user;
    }

    public Result<User> register(User user) {
        if(!FilterInput.verifyMatch(FilterInput.PASSWORD_RULES, user.getPassword())){
            return new Result<>(2000,"密码不符合格式");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userMapper.insertUser(user);
        return new Result<>(200,"用户注册成功！");
    }

    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return new Result<>(200,"已退出登录");
    }
}
