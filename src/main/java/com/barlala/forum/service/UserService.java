package com.barlala.forum.service;

import com.barlala.forum.dao.UserMapper;
import com.barlala.forum.entity.User;
import com.barlala.forum.entity.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/21 下午9:05
 */
@Service
public class UserService {
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private HttpServletResponse response;


    @Autowired
    public UserService(UserMapper userMapper, AuthenticationService authenticationService, HttpServletResponse response) {
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
        this.response = response;
    }

    public ResultJson<Object> login(boolean isUsername, String certificate, String password) {
        UserExample userExample = new UserExample();
        if (isUsername) {
            userExample.or().andUsernameEqualTo(certificate);
        } else {
            userExample.or().andEmailEqualTo(certificate);
        }
        List<User> user = userMapper.selectByExample(userExample);
        if (user != null) {
            if (password.equals(user.get(0).getPassword())) {
                Cookie cookie = new Cookie("jwt", authenticationService.createToken(user.get(0)));
                cookie.setMaxAge(86400);
                response.addCookie(cookie);
                return new ResultJson<>(HttpStatus.OK);
            } else {
                return new ResultJson<>(HttpStatus.BAD_REQUEST, "用户名/邮箱或密码错误");
            }
        } else {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "用户不存在");
        }
    }

}
