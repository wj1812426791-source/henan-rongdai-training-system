package com.rongdai.training.service;

import com.rongdai.training.entity.User;
import java.util.List;

public interface AdminUserService {
    List<User> getUsersByRole(String role);
    void addUser(User user);
    void removeUser(Integer userId);
}