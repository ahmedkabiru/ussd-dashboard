package com.hamsoft.restapi.controller;


import com.hamsoft.restapi.domain.User;

import com.hamsoft.restapi.exception.ResourceNotFoundException;
import com.hamsoft.restapi.service.MailService;
import com.hamsoft.restapi.service.UserService;
import com.hamsoft.restapi.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final  UserService userService;
    private final   MailService mailService;

    private  static  final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping
    public List<User> getAllUsers(){
        return  userService.getAllUsers();
    }


    // Get User By Id
    @GetMapping(value = "{id}")
    public User getUser(@PathVariable(value = "id") Long id) throws EntityNotFoundException
    {
      //return userService.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));

      Optional<User> user =  userService.findById(id);
      if(!user.isPresent()){
          throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        return user.get();
    }


    public User updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userDetails){
//        User user = userService.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
//        return  userService.createUser(user);
        return  null;
    }


    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Long id){
        User user= userService.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        userService.deleteUser(user);
        return  ResponseEntity.ok().build();
    }


}
