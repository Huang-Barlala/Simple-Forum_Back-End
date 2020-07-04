package com.barlala.forum.service;

import com.barlala.forum.dao.UserMapper;
import com.barlala.forum.entity.User;
import com.barlala.forum.entity.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Autowired
    public UserService(UserMapper userMapper, AuthenticationService authenticationService) {
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    public Result<?> login(boolean isUsername, String certificate, String password, HttpServletResponse response) {
        UserExample userExample = new UserExample();
        if (isUsername) {
            userExample.or().andUsernameEqualTo(certificate);
        } else {
            userExample.or().andEmailEqualTo(certificate);
        }
        try {
            List<User> user = userMapper.selectByExample(userExample);
            if (user != null) {
                if (password.equals(user.get(0).getPassword())) {
                    authenticationService.refreshCookie(user.get(0), response);
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error("用户名/邮箱或密码错误");
                }
            }
        } catch (IndexOutOfBoundsException ignore) {
        }
        return ResultUtil.error("用户名/邮箱不存在");
    }

    public boolean isUsernameRepeat(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        return !users.isEmpty();
    }

    public boolean isEmailRepeat(String email) {
        UserExample example = new UserExample();
        example.or().andEmailEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        return !users.isEmpty();
    }

    public boolean insertUser(User user) {
        int success = userMapper.insertSelective(user);
        return success == 1;
    }

    public String getUsername(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user == null ? "" : user.getUsername();
    }

    public void updateTopicNum(int userId, boolean plus) {
        User user = userMapper.selectByPrimaryKey(userId);
        int topicNum = user.getTopicnum() + (plus ? 1 : -1);
        user.setTopicnum(topicNum);
        userMapper.updateByPrimaryKey(user);
    }

    public boolean updateAvatarUrl(int userId, String avatarUrl) {
        User user = new User();
        user.setId(userId);
        user.setAvatarurl(avatarUrl);
        int result = userMapper.updateByPrimaryKeySelective(user);
        return result == 1;
    }

    public boolean updateUsername(int userId, String username) {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        int result = userMapper.updateByPrimaryKeySelective(user);
        return result == 1;
    }

    public User getUserWithoutPassword(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword("");
        return user;
    }


    public boolean updateEmail(int userId, String email) {
        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        int result = userMapper.updateByPrimaryKeySelective(user);
        return result == 1;
    }

    public boolean updatePassword(int userId, String password) {
        User user = new User();
        user.setId(userId);
        user.setPassword(password);
        int result = userMapper.updateByPrimaryKeySelective(user);
        return result == 1;
    }

    public boolean checkPassword(int userId, String password) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return false;
        }
        return password.equals(user.getPassword());
    }


}
