package com.rongdai.training.controller;

import com.rongdai.training.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@Controller
public class RankingController {

    @Autowired
    private RankingService rankingService;

    /**
     * 荣誉榜页面跳转
     */
    @GetMapping("/student/ranking")
    public String showRankingPage(Model model) {
        // 获取排行数据
        List<Map<String, Object>> rankingList = rankingService.getFullRanking();
        
        // 将数据传递给前端页面（页面通过 rankingList 变量名访问）
        model.addAttribute("rankingList", rankingList);
        
        // 返回模板路径：src/main/resources/templates/student/ranking.html
        return "student/ranking";
    }
}