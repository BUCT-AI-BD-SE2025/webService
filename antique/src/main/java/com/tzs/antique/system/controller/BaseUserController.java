package com.tzs.antique.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tzs.antique.common.controller.BaseController;
import com.tzs.antique.common.domain.QueryRequest;
import com.tzs.antique.common.exception.GymException;
import com.tzs.antique.common.utils.MD5Util;
import com.tzs.antique.system.entity.BaseUser;

import com.tzs.antique.system.service.IBaseUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author ZHZ
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
@Slf4j
public class BaseUserController extends BaseController {

    @Autowired
    private IBaseUserService baseUserService;

    @GetMapping
    @ApiOperation(value = "用户列表查询")
    public IPage<BaseUser> userList(QueryRequest request, @Valid BaseUser user){
       return baseUserService.findUserList(request,user);
    }

    @PostMapping
    public void save(@Valid BaseUser user) throws GymException {
        if(baseUserService.isExist(user.getUserName())){
            throw new GymException("用户名已存在");
        }
        baseUserService.saveUser(user);
    }

    @PutMapping
    @RequiresPermissions("用户管理")
    public void update(BaseUser user){
        baseUserService.updateUser(user);
    }

    @PutMapping("/del")
    public void updateFlag(BaseUser user){
        user.setFlag(1);
        baseUserService.updateUser(user);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(String oldPassword, String newPassword){
        BaseUser loginUser = (BaseUser) SecurityUtils.getSubject().getPrincipal();
        String userName = loginUser.getUserName();
        BaseUser userDb = baseUserService.getUserByUserName(userName);
        if (!userDb.getPassWord().equals(MD5Util.encrypt(loginUser.getUserName(), oldPassword))) {
            return ResponseEntity.badRequest().body("旧密码不正确");
        }
        userDb.setPassWord(MD5Util.encrypt(loginUser.getUserName(), newPassword));
        baseUserService.updateById(userDb);
        return ResponseEntity.ok("保存成功");
    }

//    @DeleteMapping("/{userId}")
//    public void delete(@PathVariable int userId){
//        baseUserService.deleteUser(userId);
//    }













/*******************************************************华********丽********的******分**********************割********************线********************************/


//    @GetMapping
//    public List<BaseUser> userList() {
//
//        return null;
//    }
//
//    @GetMapping("test")
//    public String cacheTest() {
//        SMS_CACHE.put("kkk", "2056");
//        return "true";
//    }
//
//    @GetMapping("kafka")
//    public boolean send(String message) {
//        kafkaTemplate.send("test", "kkkk", "66666666");
//        return true;
//    }
//
//    @GetMapping("find")
//    public void findTopoc() {
//        ListTopicsResult listTopicsResult = kafkaAdminClient.listTopics();//.listTopics();
//        KafkaFuture<Set<String>> names = listTopicsResult.names();
//        try {
//            names.get().forEach((k) -> {
//                System.out.println("topic:"+k);
//            });
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//    }


}
