package com.barlala.forum.service;

import com.barlala.forum.dao.UserMapper;
import com.barlala.forum.entity.User;
import com.barlala.forum.entity.UserExample;
import com.github.pagehelper.PageHelper;
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

    public void updateTopicNum(int userId, boolean plus) {
        User user = userMapper.selectByPrimaryKey(userId);
        int topicNum = user.getTopicNum() + (plus ? 1 : -1);
        user.setTopicNum(topicNum);
        userMapper.updateByPrimaryKey(user);
    }


    public User getUserWithoutPassword(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword("");
        return user;
    }


    public boolean checkPassword(int userId, String password) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return false;
        }
        return password.equals(user.getPassword());
    }

    private UserExample orderExample(String orderBy, boolean desc) {
        UserExample example = new UserExample();
        StringBuilder clause = new StringBuilder(orderBy);
        if (desc) {
            clause.append(" DESC");
        } else {
            clause.append(" ASC");
        }
        example.setOrderByClause(clause.toString());
        return example;
    }

    public long getAllUserPageNum(String search) {
        UserExample example = new UserExample();
        if (search != null && !search.equals("")) {
            example.or().andUsernameLike("%" + search + "%");
            example.or().andEmailLike("%" + search + "%");
            try {
                int id = Integer.parseInt(search);
                example.or().andIdEqualTo(id);
                example.or().andTopicNumEqualTo(id);
            } catch (NumberFormatException ignore) {
            }
        }
        long num = userMapper.countByExample(example);
        return num / 20 + (num % 20 == 0 ? 0 : 1);
    }

    public List<User> getAllUserList(int page, String order, boolean desc, String search) {
        UserExample example = orderExample(order, desc);
        if (search != null && !search.equals("")) {
            example.or().andUsernameLike("%" + search + "%");
            example.or().andEmailLike("%" + search + "%");
            try {
                int id = Integer.parseInt(search);
                example.or().andIdEqualTo(id);
                example.or().andTopicNumEqualTo(id);
            } catch (NumberFormatException ignore) {
            }
        }
        PageHelper.startPage(page, 20);
        return userMapper.selectByExample(example);
    }

    public boolean updateUser(User user){
        int result=userMapper.updateByPrimaryKeySelective(user);
        return result==1;
    }

    public boolean deleteUser(Integer id) {
        int result=userMapper.deleteByPrimaryKey(id);
        return result==1;
    }
}
