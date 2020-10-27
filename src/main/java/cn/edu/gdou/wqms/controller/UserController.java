package cn.edu.gdou.wqms.controller;


import cn.edu.gdou.wqms.model.User;
import cn.edu.gdou.wqms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PostMapping("/editPassword/{id}")
    public Map<String, Object> editPassword(String originPassword, String newPassword, @PathVariable("id") int id) {
        Map<String, Object> resp = new HashMap<>();
        if(userService.checkPassword(originPassword, id)) {
            if(userService.editPassword(newPassword, id)) {
                resp.put("status", "success");
            } else {
                resp.put("status", "failure");
            }
        } else {
            resp.put("status", "error");
        }
        return resp;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/grant/{id}")
    public Map<String, Object> grantAuthority(@PathVariable int id, String roleName) {
        Map<String, Object> resp = new HashMap<>();
        if(userService.grantAuthority(id, roleName)) {
            resp.put("status", "success");
        } else {
            resp.put("status", "failure");
        }
        return resp;
    }

    @PostMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable int id) {
        Map<String, Object> resp = new HashMap<>();
        if(userService.delete(id)) {
            resp.put("status", "success");
        } else {
            resp.put("status", "failure");
        }
        return resp;
    }

    @PostMapping("/register")
    public Map<String, Object> register(String username, String password, String email) {
        if (email == null || "".equals(email)){
            email = "2276230432@qq.com";
        }
        Map<String, Object> resp = new HashMap<>();
        if(userService.exists(username)) {
            resp.put("status", "duplicate");
        } else {
            if(userService.register(username, password, email)) {
                resp.put("status", "success");
            } else {
                resp.put("status", "failure");
            }
        }
        return resp;
    }

    @GetMapping("/query")
    public List<User> getQueriedUsers(String username) {
        return userService.getQueriedUsers(username);
    }
}
