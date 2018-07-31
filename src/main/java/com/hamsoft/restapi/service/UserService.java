package com.hamsoft.restapi.service;

import com.hamsoft.restapi.domain.User;
import com.hamsoft.restapi.payload.request.UserChangePasswordModel;
import com.hamsoft.restapi.payload.request.UserRequest;

import java.util.Optional;

public interface UserService {

     Optional<User> findOneByUserName(String username);

     Optional<User> findOneByEmail(String email);

     User createUser(UserRequest userRequest);

     void changePassword(User user, String password);

     Boolean checkIfValidOldPassword(User user, String password);
}
