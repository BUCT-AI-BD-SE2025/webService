package com.tzs.antique.system.controller;


import com.tzs.antique.common.domain.VueRouter;
import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.service.IBaseMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author YHS
 */
@RestController
@RequestMapping("/menu")
public class BaseMenuController {

    @Autowired
    private IBaseMenuService baseMenuService;

    @GetMapping
    public ArrayList<VueRouter<BaseMenu>> userMenu(){
        return baseMenuService.findUserMenu();
    }
}
