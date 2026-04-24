package com.rongdai.training.service.impl;

import com.rongdai.training.entity.User;
import com.rongdai.training.mapper.UserMapper;
import com.rongdai.training.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUsersByRole(String role) {
        return userMapper.findUsersByRole(role);
    }

    @Override
    public void addUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword("123456");
        }
        if ("teacher".equals(user.getRole())) {
            user.setDeptId(null);
        }
        userMapper.insertUser(user);
    }

    @Override
    public void removeUser(Integer userId) {
        userMapper.deleteUser(userId);
    }
}