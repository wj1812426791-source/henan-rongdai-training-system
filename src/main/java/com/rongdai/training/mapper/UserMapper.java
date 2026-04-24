package com.rongdai.training.mapper;

import com.rongdai.training.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    // 查询指定角色的用户列表
    @Select("SELECT u.*, d.deptName FROM Users u LEFT JOIN Departments d ON u.deptId = d.deptId WHERE u.role = #{role}")
    List<User> findUsersByRole(String role);

    // 新增用户
    @Insert("INSERT INTO Users (username, password, realName, deptId, role) " +
            "VALUES (#{username}, #{password}, #{realName}, #{deptId}, #{role})")
    void insertUser(User user);

    // 删除用户
    @Delete("DELETE FROM Users WHERE userId = #{userId}")
    void deleteUser(Integer userId);

    // 更新用户（可选）
    @Update("UPDATE Users SET realName=#{realName}, deptId=#{deptId} WHERE userId=#{userId}")
    void updateUser(User user);
}