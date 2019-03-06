package com.hamsoft.restapi.repository;

import com.hamsoft.restapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends BaseRepository<Role>{

    Role findByName(String name);
}
