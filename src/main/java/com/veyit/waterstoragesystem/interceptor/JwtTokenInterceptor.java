package com.veyit.waterstoragesystem.interceptor;

import com.alibaba.fastjson.JSON;
import com.veyit.waterstoragesystem.result.Result;
import com.veyit.waterstoragesystem.entity.po.User;
import com.veyit.waterstoragesystem.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Resource
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

//        判断token是否为空
        if (StringUtils.isEmpty(token)){
            Result result = new Result(3001,"空token");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //判断这个token所对应的用户是否存在
        User user = loginService.checkToken(token);
        if (user == null){
            Result result = new Result(3002,"用户信息过期");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //是登录状态，放行
        return true;
    }
}
