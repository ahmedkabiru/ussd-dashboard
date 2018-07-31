package com.hamsoft.restapi.controller;


import com.hamsoft.restapi.domain.User;

import com.hamsoft.restapi.exception.AppException;
import com.hamsoft.restapi.exception.BadRequestException;
import com.hamsoft.restapi.payload.request.ResetPasswordModel;
import com.hamsoft.restapi.payload.request.UserChangePasswordModel;
import com.hamsoft.restapi.payload.request.UserRequest;
import com.hamsoft.restapi.payload.response.ApiResponse;
import com.hamsoft.restapi.repository.UserRepository;
import com.hamsoft.restapi.security.SecurityUtils;
import com.hamsoft.restapi.service.MailService;
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

    private  UserService userService;
    private MailService mailService;

    @Autowired
    public UserController(UserService userService,MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
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
     Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(s -> userService.findOneByUserName(s));
        if(user.isPresent()) {
            if (!userService.checkIfValidOldPassword(user.get(), userChangePasswordModel.getOldPassword())) {
                return new ResponseEntity<>(new ApiResponse(false,"incorrect password"),HttpStatus.BAD_REQUEST);
            }else{
                userService.changePassword(user.get(),userChangePasswordModel.getNewPassword());
                return new ResponseEntity<>(new ApiResponse(true,"Password Changed Successful"),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/reset-password")
    public  void requestPasswordReset(@RequestBody String mail){

        mailService.sendPasswordResetMail(
                userService.requestPasswordReset(mail)
                        .orElseThrow(()->new BadRequestException("Email address not registered"))
        );
    }

    @PostMapping(path = "/reset-password/finish")
    @Timed
    public void finishPasswordReset(@RequestBody ResetPasswordModel resetPasswordModel) {

        Optional<User> user =
                userService.completePasswordReset(resetPasswordModel.getPassword(),resetPasswordModel.getResetKey());

        if (!user.isPresent()) {
            throw new AppException("No user was found for this reset key");
        }
    }

}
