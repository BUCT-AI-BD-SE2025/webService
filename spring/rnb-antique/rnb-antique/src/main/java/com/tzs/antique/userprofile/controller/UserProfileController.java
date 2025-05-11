package com.tzs.antique.userprofile.controller;

import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.userprofile.service.impl.IProfileService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户个人信息")
@Slf4j
public class UserProfileController {

    @Autowired
    private IProfileService profileService;

    @GetMapping("/profile")
    public Map<String, Object> getUserProfile() {
        // 获取当前登录用户的用户名（前提是已登录）
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        if (username == null) {
            // 如果用户名为空，说明用户未认证或认证失败
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 401);
            errorResponse.put("msg", "Unauthorized: User not authenticated");
            return errorResponse;
        }

        // 从数据库中查询用户
        BaseUser user = profileService.getUserProfile(username);
        if (user == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 404);
            errorResponse.put("msg", "User not found");
            return errorResponse;
        }

        // 返回用户信息
        Map<String, Object> data = new HashMap<>();
        data.put("name", user.getName());
        data.put("email", user.getEmail());
        data.put("registerDate", user.getCreateTime());
        data.put("avatar", "https://picsum.photos/120");

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", data);

        return result;
    }
    @PostMapping("/profile/update")
    public Map<String, Object> updateUserProfile(@RequestBody Map<String, String> body) {
        System.out.println("========= 调试日志 =========");
        System.out.println("profileService 为 null 吗？ " + (profileService == null));
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        System.out.println("获取到的 username: " + username);

        String name = body.get("name");
        String email = body.get("email");
        System.out.println("前端传来的 name: " + name);
        System.out.println("前端传来的 email: " + email);
        System.out.println("===========================");

        boolean updated = profileService.updateUserProfile(username, name, email);
        if (!updated) {
            return error(500, "更新失败");
        }

        return success("修改成功");
    }

    private Map<String, Object> success(Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "success");
        res.put("data", data);
        return res;
    }

    private Map<String, Object> error(int code, String msg) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", code);
        res.put("msg", msg);
        return res;
    }

    @PostMapping("/change-password")
    public Map<String, Object> changePassword(@RequestBody Map<String, String> body) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        if (username == null) {
            return error(401, "Unauthorized: User not authenticated");
        }

        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");

        boolean success = profileService.changePassword(username, oldPassword, newPassword);
        if (!success) {
            return error(400, "原密码错误或更新失败");
        }

        return success("密码修改成功");
    }

}
