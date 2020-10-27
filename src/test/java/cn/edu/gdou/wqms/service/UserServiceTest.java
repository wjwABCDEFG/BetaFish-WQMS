package cn.edu.gdou.wqms.service;

import cn.edu.gdou.wqms.model.User;
import cn.edu.gdou.wqms.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void addUser() {
        User user = new User();
        String password = "1234";
        user.setRole(roleRepository.findRoleByName("user"));
        user.setUsername("user1");
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        System.out.println(userService.addUser(user));
    }
}