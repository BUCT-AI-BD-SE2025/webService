package com.tzs.antique;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Directory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@EnableCaching
@Transactional
@SpringBootApplication
@MapperScan("com.tzs.antique.*.dao")
public class AntiqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntiqueApplication.class, args);
    }

}
