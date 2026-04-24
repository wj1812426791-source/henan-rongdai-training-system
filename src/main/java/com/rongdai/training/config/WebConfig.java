package com.rongdai.training.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 1. 视频资源映射
         * 将 URL 路径中的请求映射到 D 盘的物理文件夹
         * 这样 <video src="/xxx.mp4"> 就能找到 D:/training/uploads/xxx.mp4
         */
        registry.addResourceHandler("/**")
                .addResourceLocations("file:D:/training/uploads/");

        /**
         * 2. 静态资源映射 (修复 JS/CSS 404 的关键)
         * 必须显式指定 /js/** 这种路径去 classpath:/static/ 下寻找
         */
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        /**
         * 3. 备用静态资源放行
         * 确保 Spring Boot 默认的 static 目录依然生效
         */
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}