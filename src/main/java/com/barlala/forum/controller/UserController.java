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
    private final String ADMIN = "admin";
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
        user.setCreateTime(new Date());
        user.setTopicNum(0);
        user.setAvatarUrl("/api/image/5b6d5b39274f0_610.jpg");
        user.setPermission("user");
        if (userService.insertUser(user)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error();
        }
    }

    @GetMapping("/api/user")
    public Result<?> getUserDetail(@CookieValue(value = "jwt") String jwt) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        return ResultUtil.success(userService.getUserWithoutPassword(userId));
    }

    @PutMapping("/api/user")
    public Result<?> updateUser(@CookieValue String jwt,
                                @RequestParam(value = "id") Integer id,
                                @RequestParam(value = "username", required = false) String username,
                                @RequestParam(value = "email", required = false) String email,
                                @RequestParam(value = "avatarUrl", required = false) String avatarUrl,
                                @RequestParam(value = "password", required = false) String password,
                                HttpServletResponse response) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId != id && !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        User user = new User();
        if (username != null && username.equals("")) {
            username = null;
        }
        if (email != null && email.equals("")) {
            email = null;
        }
        if (avatarUrl != null && avatarUrl.equals("")) {
            avatarUrl = null;
        }
        if (password != null && password.equals("")) {
            password = null;
        }
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setAvatarUrl(avatarUrl);
        user.setPassword(password);
        if (userService.updateUser(user)) {
            if (username != null) {
                authenticationService.refreshCookie(userService.getUserWithoutPassword(userId), response);
                topicService.updateAuthor(id, username);
                replyService.updateAuthor(id, username);
            }
            if (avatarUrl != null) {
                authenticationService.refreshCookie(userService.getUserWithoutPassword(userId), response);
                topicService.updateAvatar(id, avatarUrl);
                replyService.updateAvatar(id, avatarUrl);
            }
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    @DeleteMapping("/api/user")
    public Result<?> deleteUser(@CookieValue String jwt,
                                @RequestParam(value = "id") Integer id) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId != id && !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        if (userService.deleteUser(id)) {
            topicService.updateAuthor(id, "(已注销)");
            replyService.updateAuthor(id, "(已注销)");
            topicService.updateAvatar(id, "/api/image/5b6d5b39274f0_610.jpg");
            replyService.updateAvatar(id, "/api/image/5b6d5b39274f0_610.jpg");
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }


    @GetMapping("/api/usernameCheck")
    public Result<?> usernameRepetition(@RequestParam(value = "username") String username) {
        return ResultUtil.success(!userService.isUsernameRepeat(username));
    }

    @GetMapping("/api/emailCheck")
    public Result<?> emailRepetition(@RequestParam(value = "email") String email) {
        return ResultUtil.success(!userService.isEmailRepeat(email));
    }


    @GetMapping("/api/passwordConfirmation")
    public Result<?> checkPassword(@CookieValue(value = "jwt") String jwt,
                                   @RequestParam(value = "password") String password) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        return userService.checkPassword(userId, password) ? ResultUtil.success() : ResultUtil.error();
    }

    @GetMapping("/api/allUserPageNum")
    public Result<?> getAllUserPageNum(@CookieValue String jwt,
                                       @RequestParam(value = "search", required = false) String search) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1 || !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        return ResultUtil.success(userService.getAllUserPageNum(search));
    }

    @GetMapping("/api/allUser")
    public Result<?> getAllUser(@CookieValue String jwt,
                                @RequestParam(value = "page", required = false) Integer page,
                                @NotBlank @RequestParam(value = "order", required = false) String order,
                                @RequestParam(value = "desc", required = false) Boolean desc,
                                @RequestParam(value = "search", required = false) String search) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1 || !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        if (page == null) {
            page = 1;
        }
        if (page > userService.getAllUserPageNum(search)) {
            return ResultUtil.error("页数超出范围");
        }
        if (order == null) {
            order = "id";
        }
        if (desc == null) {
            desc = false;
        }
        return ResultUtil.success(userService.getAllUserList(page, order, desc, search));
    }
}
