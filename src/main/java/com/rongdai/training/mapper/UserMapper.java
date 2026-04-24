package com.rongdai.training.mapper;

import com.rongdai.training.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    // 查询指定角色的用户列表
    @Select("SELECT u.*, d.deptName FROM Users u LEFT JOIN Departments d ON u.deptId = d.deptId WHERE u.role = #{role}")
    List<User> findUsersByRole(String role);

    // 获取所有部门供下拉框选择
    @Select("SELECT deptId, deptName FROM Departments")
    List<Map<String, Object>> findAllDepartments();

    // 根据用户名查询（登录验证及查重）
    @Select("SELECT * FROM Users WHERE username = #{username}")
    User findByUsername(String username);

    // 新增用户
    @Insert("INSERT INTO Users (username, password, realName, role, deptId) " +
            "VALUES (#{username}, #{password}, #{realName}, #{role}, #{deptId})")
    void insertUser(User user);

    // 删除用户
    @Delete("DELETE FROM Users WHERE userId = #{userId}")
    void deleteUser(Integer userId);

    // 更新用户
    @Update("UPDATE Users SET username=#{username}, password=#{password}, realName=#{realName} WHERE userId=#{userId}")
    void updateUser(User user);
}