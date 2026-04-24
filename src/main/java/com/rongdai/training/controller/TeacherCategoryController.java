package com.rongdai.training.controller;

import com.rongdai.training.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacher")
public class TeacherCategoryController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/categories")
    public String categoryPage(Model model) {
        model.addAttribute("categories", courseMapper.findAllCategories());
        return "teacher/category_manage";
    }

    @PostMapping("/category/add")
    public String addCategory(@RequestParam String categoryName) {
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            courseMapper.insertCategory(categoryName.trim());
        }
        return "redirect:/teacher/categories";
    }

    @PostMapping("/category/delete/{id}")
    @ResponseBody
    public String deleteCategory(@PathVariable Integer id) {
        courseMapper.deleteCategory(id);
        return "success";
    }

    @PostMapping("/category/update")
    @ResponseBody
    public String updateCategory(@RequestParam Integer categoryId, @RequestParam String categoryName) {
        courseMapper.updateCategory(categoryId, categoryName);
        return "success";
    }
}
