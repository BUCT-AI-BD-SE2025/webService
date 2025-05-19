package com.tzs.antique.system.controller;

import com.tzs.antique.system.entity.PieData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许跨域请求，前后端分离时必须加
public class ChartController {

   // @Resource
//    private ChartService ChartService;
    @GetMapping("/pie-data")
    public List<PieData> getPieChartData() {

        //现在数据库里边只有大都会博物馆，不在数据库里查了，直接返回
        return Arrays.asList(
                new PieData(3350, "大都会博物馆")

        );

    }
}