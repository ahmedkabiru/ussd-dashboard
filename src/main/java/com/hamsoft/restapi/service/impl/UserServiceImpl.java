package com.hamsoft.restapi.service.impl;

import com.hamsoft.restapi.domain.Role;
import com.hamsoft.restapi.domain.User;
import com.hamsoft.restapi.payload.request.UserChangePasswordModel;
import com.hamsoft.restapi.payload.request.UserRequest;
import com.hamsoft.restapi.repository.RoleRepository;
import com.hamsoft.restapi.repository.UserRepository;
import com.hamsoft.restapi.security.AuthoritiesConstants;
import com.hamsoft.restapi.security.SecurityUtils;
import com.hamsoft.restapi.service.UserService;
import com.hamsoft.restapi.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);


    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findOneByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public User createUser(UserRequest userRequest) {

        User user = new User();
        Role role =  roleRepository.findByName(AuthoritiesConstants.USER);
        Set<Role> roles = new HashSet<>();
        String encryptedPassword =passwordEncoder.encode(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getFirstName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(encryptedPassword);
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }


    @Override
    public void changePassword(User user, String password) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
    }

    @Override
    public Boolean checkIfValidOldPassword(User user, String password) {
            return  passwordEncoder.matches(password,user.getPassword());
    }

    @Override
    public Optional<User> requestPasswordReset(String email) {
        return  userRepository.findByEmail(email).map(user -> {
            user.setResetKey(RandomUtil.generateResetKey());
            user.setResetDate(Instant.now());
            return  user;
        });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userRepository.findByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    return user;
                });
    }

}
