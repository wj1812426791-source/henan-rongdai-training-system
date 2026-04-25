package com.rongdai.training.controller;

import com.rongdai.training.entity.User;
import com.rongdai.training.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if ("student".equals(user.getRole()) && user.getDeptId() != null) {
            String deptName = userMapper.getDeptNameById(user.getDeptId());
            model.addAttribute("deptName", deptName);
        }

        return "user/profile";
    }

    @PostMapping("/updateProfile")
    @ResponseBody
    public Map<String, Object> updateProfile(HttpSession session,
                                              @RequestParam Integer userId,
                                              @RequestParam String username,
                                              @RequestParam String realName,
                                              @RequestParam(required = false) String password) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = new User();
            user.setUserId(userId);
            user.setUsername(username);
            user.setRealName(realName);

            if (password != null && !password.isEmpty()) {
                userMapper.updatePassword(userId, password);
            }
            userMapper.updateProfile(user);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
