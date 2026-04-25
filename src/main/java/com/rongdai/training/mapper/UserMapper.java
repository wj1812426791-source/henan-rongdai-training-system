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

    // 联表查询：查出学员信息及其所属部门名称
    @Select("SELECT u.*, d.deptName FROM Users u " +
            "LEFT JOIN Departments d ON u.deptId = d.deptId " +
            "WHERE u.role = 'student'")
    List<Map<String, Object>> findAllStudentsWithDept();

    // 更新学员所属部门
    @Update("UPDATE Users SET deptId = #{deptId} WHERE userId = #{userId}")
    void updateStudentDept(@Param("userId") Integer userId, @Param("deptId") Integer deptId);

    // 获取所有部门（用于编辑时的下拉框）
    @Select("SELECT * FROM Departments")
    List<Map<String, Object>> findAllDepts();

    // 更新学员基础信息（含部门）
    @Update("UPDATE Users SET username=#{username}, realName=#{realName}, " +
            "password=#{password}, deptId=#{deptId} WHERE userId=#{userId}")
    void updateStudent(User user);

    // 删除学员
    @Delete("DELETE FROM Users WHERE userId = #{userId} AND role = 'student'")
    void deleteStudent(Integer userId);

    // 统计学员总数
    @Select("SELECT COUNT(*) FROM Users WHERE role = 'student'")
    int countStudents();

    // 统计所有用户数量
    @Select("SELECT COUNT(*) FROM Users")
    int countAllUsers();

    // 根据部门ID获取部门名称
    @Select("SELECT deptName FROM Departments WHERE deptId = #{deptId}")
    String getDeptNameById(Integer deptId);

    // 更新用户资料（个人中心）
    @Update("UPDATE Users SET username=#{username}, realName=#{realName} WHERE userId=#{userId}")
    void updateProfile(User user);

    // 修改密码
    @Update("UPDATE Users SET password=#{password} WHERE userId=#{userId}")
    void updatePassword(@Param("userId") Integer userId, @Param("password") String password);
}