package com.rongdai.training.service;

import com.rongdai.training.entity.User;
import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Map<String, Object>> getAllDepartments();
    boolean register(User user);
    User login(String username, String password);
}
