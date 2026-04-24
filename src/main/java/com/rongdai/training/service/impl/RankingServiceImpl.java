package com.rongdai.training.service.impl;

import com.rongdai.training.mapper.RankingMapper;
import com.rongdai.training.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    private RankingMapper rankingMapper;

    @Override
    public List<Map<String, Object>> getFullRanking() {
        // 直接返回 List<Map>，MyBatis 会自动把 SQL 的列名作为 Key
        return rankingMapper.getCreditRanking();
    }
}