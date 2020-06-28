package com.barlala.forum.controller;

import com.barlala.forum.entity.User;
import com.barlala.forum.service.ResultJson;
import com.barlala.forum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;


/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/21 下午8:23
 */
@RestController
@Validated
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login/username")
    public ResultJson<?> loginByUsername(@NotBlank @RequestParam(value = "username") String username,
                                         @NotBlank @RequestParam(value = "password") String password) {
        return userService.login(true, username, password);
    }

    @PostMapping(value = "/login/email")
    public ResultJson<?> loginByEmail(@NotBlank @RequestParam(value = "email") String email,
                                      @NotBlank @RequestParam(value = "password") String password) {
        return userService.login(false, email, password);
    }

    @GetMapping(value = "/logout")
    public ResultJson<Object> logout(@CookieValue(value = "jwt") String jwt
            , HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResultJson<>(HttpStatus.OK, "登出成功");
    }

    @PostMapping(value = "register")
    public ResultJson<?> register(@RequestParam(value = "username") String username,
                                  @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z~!@#$%^&*]{8,32}$", message = "密码格式错误") @RequestParam(value = "password") String password,
                                  @Email @RequestParam(value = "email", required = false) String email) {
        if (userService.isUsernameRepeat(username)) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "用户名重复");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (email != null) {
            if (userService.isEmailRepeat(email)) {
                return new ResultJson<>(HttpStatus.BAD_REQUEST, "邮箱已被使用");
            }
            user.setEmail(email);
        }
        user.setCreatetime(new Date());
        user.setTopicnum(0);
        if (userService.insertUser(user)) {
            return new ResultJson<>(HttpStatus.OK, "注册成功");
        } else {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "注册失败");
        }
    }


}
