package com.barlala.forum.controller;

import com.barlala.forum.service.ResultJson;
import com.barlala.forum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


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

    @PostMapping(value = "/login/username")
    public ResultJson<?> loginByUsername(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password) {
        return userService.login(true, username, password);
    }

    @PostMapping(value = "/login/email")
    public ResultJson<?> loginByEmail(@RequestParam(value = "email") String email,
                                      @RequestParam(value = "password") String password) {
        return userService.login(false, email, password);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultJson<Object> logout(@CookieValue(value = "jwt") String jwt
            , HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResultJson<>(HttpStatus.OK, "登出成功");
    }


}
