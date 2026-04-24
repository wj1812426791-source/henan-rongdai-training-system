package com.rongdai.training.service.impl;

import com.rongdai.training.entity.User;
import com.rongdai.training.mapper.UserMapper;
import com.rongdai.training.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Map<String, Object>> getAllDepartments() {
        return userMapper.findAllDepartments();
    }

    @Override
    public boolean register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) return false;
        user.setRole("student");
        userMapper.insertUser(user);
        return true;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) return user;
        return null;
    }
}
