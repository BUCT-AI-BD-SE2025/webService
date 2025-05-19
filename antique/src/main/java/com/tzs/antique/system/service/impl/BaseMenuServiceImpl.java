package com.tzs.antique.system.service.impl;

import com.tzs.antique.common.domain.VueRouter;
import com.tzs.antique.common.utils.TreeUtil;
import com.tzs.antique.system.dao.BaseMenuMapper;
import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.service.IBaseMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YHS
 */
@Service
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, BaseMenu> implements IBaseMenuService {

    @Autowired(required = false)
    private BaseMenuMapper menuMapper;

    @Override
    public ArrayList<VueRouter<BaseMenu>> findUserMenu() {
        BaseUser loginUser = (BaseUser) SecurityUtils.getSubject().getPrincipal();
        List<BaseMenu> menus = menuMapper.findUserMenus(loginUser.getRoles());
        ArrayList<VueRouter<BaseMenu>> routes = new ArrayList<>();
        menus.forEach(menu ->{
            VueRouter<BaseMenu> route = new VueRouter<>();
            route.setName(menu.getMenu());
            route.setParentId(menu.getParentId());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setIcon(menu.getIcon());
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }
}
