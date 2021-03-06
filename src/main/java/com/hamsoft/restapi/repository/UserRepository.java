package com.hamsoft.restapi.repository;

import com.hamsoft.restapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User>  findByUsername(String username);

    Optional<User>  findByEmail(String email);

    Optional<User> findByResetKey(String resetKey);


}
