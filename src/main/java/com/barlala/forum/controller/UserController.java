package com.barlala.forum.controller;

import com.barlala.forum.service.ResultJson;
import com.barlala.forum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/21 下午8:23
 */
@RestController
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResultJson<Object> login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "email", required = false) String email) {
        logger.info("开始处理登录");
        if (password == null) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST);
        } else {
            if (username != null) {
                return userService.loginByUsername(username, password);
            } else if (email != null) {
                return userService.loginByEmail(email, password);
            } else {
                return new ResultJson<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultJson<Object> logout(@CookieValue(value = "jwt", required = false) String jwt
            , HttpServletResponse response) {
        if (jwt == null) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST);
        } else {
            Cookie cookie = new Cookie("jwt", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return new ResultJson<>(HttpStatus.OK, "登出成功");
        }
    }


}
