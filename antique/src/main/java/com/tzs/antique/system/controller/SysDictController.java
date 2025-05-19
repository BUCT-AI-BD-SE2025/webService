package com.tzs.antique.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tzs.antique.system.dao.SysDictMapper;
import com.tzs.antique.system.entity.SysDict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZHZ
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "字典表查询")
public class SysDictController {

    @Autowired
    private SysDictMapper dictMapper;

    @ApiOperation("列表查询")
    @GetMapping("/list")
    public List<SysDict> list(@RequestParam String type){
        LambdaQueryWrapper<SysDict> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(SysDict::getType, type);
        return dictMapper.selectList(lambdaQueryWrapper);
    }
}
