package com.rongdai.training.controller;

import com.rongdai.training.entity.Department;
import com.rongdai.training.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/dept")
public class DeptController {

    @Autowired
    private DeptMapper deptMapper;

    @GetMapping("/manage")
    public String deptManagePage(Model model) {
        model.addAttribute("depts", deptMapper.selectAllDepts());
        return "admin/dept_manage";
    }

    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> getAllDepts() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", deptMapper.selectAllDepts());
        return result;
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addDept(Department dept) {
        Map<String, Object> result = new HashMap<>();
        try {
            deptMapper.insertDept(dept);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateDept(Department dept) {
        Map<String, Object> result = new HashMap<>();
        try {
            deptMapper.updateDept(dept);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteDept(@RequestParam Integer deptId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int userCount = deptMapper.countUsersByDept(deptId);
            if (userCount > 0) {
                result.put("success", false);
                result.put("message", "该部门下有 " + userCount + " 名学员，无法删除！");
                return result;
            }
            deptMapper.deleteDept(deptId);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
