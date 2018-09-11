package com.hamsoft.restapi.controller;

import com.hamsoft.restapi.domain.User;
import com.hamsoft.restapi.exception.AppException;
import com.hamsoft.restapi.exception.BadRequestException;
import com.hamsoft.restapi.payload.JWTToken;
import com.hamsoft.restapi.payload.request.LoginRequest;
import com.hamsoft.restapi.payload.request.ResetPasswordModel;
import com.hamsoft.restapi.payload.request.UserChangePasswordModel;
import com.hamsoft.restapi.payload.request.UserRequest;
import com.hamsoft.restapi.payload.response.ApiResponse;
import com.hamsoft.restapi.security.SecurityUtils;
import com.hamsoft.restapi.security.jwt.JwtAuthenticationFilter;
import com.hamsoft.restapi.security.jwt.JwtTokenProvider;
import com.hamsoft.restapi.service.MailService;
import com.hamsoft.restapi.service.UserService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final MailService mailService;
    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

//    @Autowired
//    public AuthController(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
//        this.tokenProvider = tokenProvider;
//        this.authenticationManager = authenticationManager;
//    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginRequest.isRememberMe() == null) ? false : loginRequest.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity createUser(@Valid @RequestBody UserRequest userRequest){

        if(userService.findOneByUserName(userRequest.getUsername()).isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"Username is already Taken"), HttpStatus.BAD_REQUEST);
        }
        if(userService.findOneByEmail(userRequest.getEmail()).isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"Email is already Taken"), HttpStatus.BAD_REQUEST);

        }

        User newUser = userService.createUser(userRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

    }



    @PostMapping(path = "/change-password")
    @Timed
    public  ResponseEntity<?> changePassword(@Valid @RequestBody UserChangePasswordModel userChangePasswordModel){
        Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(userService::findOneByUserName);
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
