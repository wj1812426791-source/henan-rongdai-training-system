package com.rongdai.training.service;

import java.util.List;
import java.util.Map;

public interface RankingService {
    /**
     * 获取全员学分排行列表（基于 Map 动态映射）
     */
    List<Map<String, Object>> getFullRanking();
}