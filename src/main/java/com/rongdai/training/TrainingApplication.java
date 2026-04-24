package com.rongdai.training;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 河南融代员工培训管理系统 - 主启动类
 */
@SpringBootApplication
@MapperScan("com.rongdai.training.mapper") // 关键：自动扫描 Mapper 接口
public class TrainingApplication {

    public static void main(String[] args) {
        // 启动 Spring 应用
        SpringApplication.run(TrainingApplication.class, args);
        
        System.out.println("========================================");
        System.out.println("   河南融代培训系统启动成功！");
        System.out.println("   访问地址：http://localhost:8080");
        System.out.println("========================================");
    }
}