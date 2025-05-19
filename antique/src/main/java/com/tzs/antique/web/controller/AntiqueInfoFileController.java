package com.tzs.antique.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tzs.antique.web.dao.AntiqueInfoFileMapper;
import com.tzs.antique.web.entity.AntiqueInfo;
import com.tzs.antique.web.entity.AntiqueInfoFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YHS
 */
@RestController
@RequestMapping("/antique-info-file")
public class AntiqueInfoFileController {

    @Resource
    private AntiqueInfoFileMapper mapper;

    @GetMapping("/list/{antiqueId}")
    public ResponseEntity list(@PathVariable Integer antiqueId){
        LambdaQueryWrapper<AntiqueInfoFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AntiqueInfoFile::getAntiqueId, antiqueId);
        List<AntiqueInfoFile> antiqueInfoFiles = mapper.selectList(lambdaQueryWrapper);
        return ResponseEntity.ok(antiqueInfoFiles);
    }
}
