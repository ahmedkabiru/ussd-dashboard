package com.hamsoft.restapi.service;

import com.hamsoft.restapi.domain.User;
import com.hamsoft.restapi.payload.request.UserChangePasswordModel;
import com.hamsoft.restapi.payload.request.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {


     List<User> getAllUsers();

     void deleteUser(User user);

     Optional<User> findById(Long id);

     void deleteAllUsers();

     Optional<User> findOneByUserName(String username);

     Optional<User> findOneByEmail(String email);

     User createUser(UserRequest userRequest);

     void changePassword(User user, String password);

     Boolean checkIfValidOldPassword(User user, String password);

     Optional<User> requestPasswordReset(String email);

     Optional<User> completePasswordReset(String newPassword, String key);
}
