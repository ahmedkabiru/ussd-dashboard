package com.hamsoft.restapi.repository;

import com.hamsoft.restapi.domain.Category;
import com.hamsoft.restapi.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category>{

}
