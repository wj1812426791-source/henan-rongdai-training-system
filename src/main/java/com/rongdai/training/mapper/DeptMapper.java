package com.rongdai.training.mapper;

import com.rongdai.training.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeptMapper {

    @Select("SELECT * FROM Departments ORDER BY deptId DESC")
    List<Map<String, Object>> selectAllDepts();

    @Insert("INSERT INTO Departments (deptName, description) VALUES (#{deptName}, #{description})")
    int insertDept(Department dept);

    @Update("UPDATE Departments SET deptName=#{deptName}, description=#{description} WHERE deptId=#{deptId}")
    int updateDept(Department dept);

    @Delete("DELETE FROM Departments WHERE deptId = #{deptId}")
    int deleteDept(Integer deptId);

    @Select("SELECT COUNT(*) FROM Users WHERE deptId = #{deptId}")
    int countUsersByDept(Integer deptId);

    @Select("SELECT COUNT(*) FROM Departments")
    int countAllDepts();
}
