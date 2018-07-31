package com.hamsoft.restapi.repository;

import com.hamsoft.restapi.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
