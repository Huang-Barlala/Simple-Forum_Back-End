package com.barlala.forum.service;

import com.barlala.forum.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/21 下午9:05
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


}
