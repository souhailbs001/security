package com.bfi.security.service;


import com.bfi.security.dao.RoleDao;
import com.bfi.security.dao.UserDao;
import com.bfi.security.entity.Role;
import com.bfi.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("chef_agence_role");
        adminRole.setRoleDescription("Default role for newly created record");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("responsable_clientele_role");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("chef_agence");
        adminUser.setUserPassword(getEncodedPassword("1234"));
        adminUser.setUserFirstName("souhail");
        adminUser.setUserLastName("ben slimen");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user = new User();
        user.setUserName("responsable_clientele");
        user.setUserPassword(getEncodedPassword("1234"));
        user.setUserFirstName("souhail");
        user.setUserLastName("ben slimen");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);
    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
