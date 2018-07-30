package com.hamsoft.restapi.security;

import com.hamsoft.restapi.domain.User;
import com.hamsoft.restapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/* This class Retrieve user-related data */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger LOG = LoggerFactory.getLogger(getClass().getSimpleName());


   private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(()->new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
        return  UserPrincipal.create(user);
    }


    @Transactional
    public  UserDetails loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return UserPrincipal.create(user);
    }
}
