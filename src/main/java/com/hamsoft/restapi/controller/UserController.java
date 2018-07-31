package com.hamsoft.restapi.controller;


import com.hamsoft.restapi.domain.User;

import com.hamsoft.restapi.exception.AppException;
import com.hamsoft.restapi.payload.request.UserChangePasswordModel;
import com.hamsoft.restapi.payload.request.UserRequest;
import com.hamsoft.restapi.payload.response.ApiResponse;
import com.hamsoft.restapi.repository.UserRepository;
import com.hamsoft.restapi.security.SecurityUtils;
import com.hamsoft.restapi.service.UserService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest){

        if(userService.findOneByUserName(userRequest.getUsername()).isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"Username is already Taken"), HttpStatus.BAD_REQUEST);
        }
        if(userService.findOneByEmail(userRequest.getEmail()).isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"Email is already Taken"), HttpStatus.BAD_REQUEST);

        }

        User newUser = userService.createUser(userRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(newUser.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));


    }

    @PostMapping(path = "/change-password")
    @Timed
    public  ResponseEntity<?> changePassword(@Valid @RequestBody UserChangePasswordModel userChangePasswordModel){
     Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(s -> userRepository.findByUsername(s));
        if(user.isPresent()) {
            if (!userService.checkIfValidOldPassword(user.get(), userChangePasswordModel.getOldPassword())) {
                return new ResponseEntity<>(new ApiResponse(false,"incorrect password"),HttpStatus.BAD_REQUEST);
            }else{
                userService.changePassword(user.get(),userChangePasswordModel.getNewPassword());
                return new ResponseEntity<>(new ApiResponse(true,"Password Changed Successful"),HttpStatus.OK);
            }
        }
        return  null;
    }

}
