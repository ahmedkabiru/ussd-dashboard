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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final  UserService userService;
    private final   MailService mailService;

//    @Autowired
//    public UserController(UserService userService,MailService mailService) {
//        this.userService = userService;
//        this.mailService = mailService;
//    }

    @GetMapping
    public List<User> getAllUsers(){
        return  userService.getAllUsers();
    }



}
