package com.barlala.forum.controller;

import com.barlala.forum.dao.UserMapper;
import com.barlala.forum.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;


/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/21 下午8:23
 */
@Controller
public class UserController {
    final UserMapper userMapper;
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password) {
        Map<String, String> map = new HashMap<>();
        map.put("status", "fail");
        logger.info("开始处理登录");
        if (username != null && password != null) {
            logger.info(username+"and"+password);
            User user = userMapper.selectByUsername(username);
            assert user != null;
            if (password.equals(user.getPassword())) {
                map.put("status", "success");
            }
        }
        return map;
    }
}
