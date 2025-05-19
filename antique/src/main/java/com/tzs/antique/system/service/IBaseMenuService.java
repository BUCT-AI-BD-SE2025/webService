package com.tzs.antique.system.service;

import com.tzs.antique.common.domain.VueRouter;
import com.tzs.antique.system.entity.BaseMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * @author YHS
 */
public interface IBaseMenuService extends IService<BaseMenu> {
    /***
     *   获取用户菜单
     * @return
     */
    ArrayList<VueRouter<BaseMenu>> findUserMenu();
}
