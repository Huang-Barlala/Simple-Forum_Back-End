package com.barlala.forum.controller;

import com.barlala.forum.entity.User;
import com.barlala.forum.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AuthenticationService authenticationService;
    private final TopicService topicService;
    private final ReplyService replyService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserService userService,
                          AuthenticationService authenticationService,
                          TopicService topicService,
                          ReplyService replyService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.topicService = topicService;
        this.replyService = replyService;
    }

    @PostMapping(value = "/api/login/username")
    public Result<?> loginByUsername(@NotBlank @RequestParam(value = "username") String username,
                                     @NotBlank @RequestParam(value = "password") String password,
                                     HttpServletResponse response) {
        return userService.login(true, username, password, response);
    }

    @PostMapping(value = "/api/login/email")
    public Result<?> loginByEmail(@NotBlank @RequestParam(value = "email") String email,
                                  @NotBlank @RequestParam(value = "password") String password,
                                  HttpServletResponse response) {
        return userService.login(false, email, password, response);
    }

    @GetMapping(value = "/api/logout")
    public Result<?> logout(@CookieValue(value = "jwt") String jwt
            , HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResultUtil.success();
    }

    @PostMapping(value = "/api/register")
    public Result<?> register(@RequestParam(value = "username") String username,
                              @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z~!@#$%^&*]{8,32}$", message = "密码格式错误") @RequestParam(value = "password") String password,
                              @Email @RequestParam(value = "email", required = false) String email) {
        if (userService.isUsernameRepeat(username)) {
            return ResultUtil.error("用户名重复");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (email != null) {
            if (userService.isEmailRepeat(email)) {
                return ResultUtil.error("邮箱已被使用");
            }
            user.setEmail(email);
        }
        user.setCreatetime(new Date());
        user.setTopicnum(0);
        user.setAvatarurl("/api/image/5b6d5b39274f0_610.jpg");
        user.setPermission("user");
        if (userService.insertUser(user)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error();
        }
    }

    @GetMapping("/api/getUserDetail")
    public Result<?> getUserDetail(@CookieValue(value = "jwt") String jwt) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        return ResultUtil.success(userService.getUserWithoutPassword(userId));
    }

    @PostMapping("/api/usernameCheck")
    public Result<?> usernameRepetition(@RequestParam(value = "username") String username) {
        return ResultUtil.success(!userService.isUsernameRepeat(username));
    }

    @PostMapping("/api/emailCheck")
    public Result<?> emailRepetition(@RequestParam(value = "email") String email) {
        return ResultUtil.success(!userService.isEmailRepeat(email));
    }

    @PostMapping("/api/updateAvatar")
    public Result<?> updateAvatar(@CookieValue(value = "jwt") String jwt,
                                  @RequestParam(value = "avatarUrl") String avatarUrl,
                                  HttpServletResponse response) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        if (userService.updateAvatarUrl(userId, avatarUrl)) {
            authenticationService.refreshCookie(userService.getUserWithoutPassword(userId), response);
            topicService.updateAvatar(userId, avatarUrl);
            replyService.updateAvatar(userId, avatarUrl);
            return ResultUtil.success();
        } else {
            return ResultUtil.error();
        }
    }

    @PostMapping("/api/updateUsername")
    public Result<?> updateUsername(@CookieValue(value = "jwt") String jwt,
                                    @RequestParam(value = "username") String username,
                                    HttpServletResponse response) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        if (userService.updateUsername(userId, username)) {
            authenticationService.refreshCookie(userService.getUserWithoutPassword(userId), response);
            topicService.updateAuthor(userId, username);
            replyService.updateAuthor(userId, username);
            return ResultUtil.success();
        } else {
            return ResultUtil.error();
        }
    }

    @PostMapping("/api/updateEmail")
    public Result<?> updateEmail(@CookieValue(value = "jwt") String jwt,
                                 @RequestParam(value = "email") String email) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        return userService.updateEmail(userId, email) ? ResultUtil.success() : ResultUtil.error();
    }

    @PostMapping("/api/updatePassword")
    public Result<?> updatePassword(@CookieValue(value = "jwt") String jwt,
                                    @RequestParam(value = "password") String password) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        return userService.updatePassword(userId, password) ? ResultUtil.success() : ResultUtil.error();
    }

    @PostMapping("/api/checkPassword")
    public Result<?> checkPassword(@CookieValue(value = "jwt") String jwt,
                                   @RequestParam(value = "password") String password) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        return userService.checkPassword(userId, password) ? ResultUtil.success() : ResultUtil.error();
    }
}
