//package com.zhz.gymnasium.common.config;
//
//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;
//import com.zhz.gymnasium.system.entity.BaseUser;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class CacheConfig {
//
//    /***
//     * 登录token保存日期
//     */
//    @Bean
//    public Cache<String, BaseUser> LOGIN_CACHE(){
//        return CacheBuilder.newBuilder()
//                .expireAfterWrite(7, TimeUnit.DAYS).build();
//    }
//    /***
//     * 短信类保存日期
//     */
//    @Bean
//    public static Cache<String, String> SMS_CACHE(){
//        return CacheBuilder.newBuilder()
//                .expireAfterWrite(30,TimeUnit.SECONDS).build();
//    }
//
//
//    @Bean
//    RestTemplate restTemplate(){
//        return new RestTemplate();
//    }
//}
