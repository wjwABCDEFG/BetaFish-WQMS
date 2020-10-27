package cn.edu.gdou.wqms.service;

import cn.edu.gdou.wqms.repository.RoleRepository;
import cn.edu.gdou.wqms.repository.UserRepository;
import cn.edu.gdou.wqms.model.Role;
import cn.edu.gdou.wqms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);
        if(user == null) {
            return new User();
        }
        return user;
    }

    public Boolean addUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public Boolean editPassword(String password, int id) {
        try{
            User user = userRepository.findUserById(id);
            user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean checkPassword(String password, int id) {
        User user = userRepository.findUserById(id);
        if(user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Boolean grantAuthority(int uid, String roleName) {
        try {
            Role role = roleRepository.findRoleByName(roleName);
            User user = userRepository.findUserById(uid);
            user.setRole(role);
            userRepository.save(user);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean exists(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public Boolean register(String username, String password, String email) {
        try {
            String encodedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            User user = new User();
            user.setUsername(username);
            user.setPassword(encodedPassword);
            user.setEmail(email);
            user.setRole(roleRepository.findRoleByName("user"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getQueriedUsers(String username) {
        return userRepository.findByUsernameContaining(username);
    }

}
