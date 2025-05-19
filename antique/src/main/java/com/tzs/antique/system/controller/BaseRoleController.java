package com.tzs.antique.system.controller;


import com.tzs.antique.system.entity.BaseRole;
import com.tzs.antique.system.service.IBaseRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YHS
 */
@RestController
@RequestMapping("/role")
public class BaseRoleController {

    @Autowired
    private IBaseRoleService roleService;

    @GetMapping
    public List<BaseRole> roles(){
        return roleService.findRoleLiist();
    }
}
