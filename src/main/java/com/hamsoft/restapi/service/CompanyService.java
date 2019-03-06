package com.hamsoft.restapi.service;

import com.hamsoft.restapi.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    List<Company> listAllCompanies();

    Optional<Company> getCompanyById(Long id);

    Company saveCompany(Company company);

    void deleteCompany(Long id);
}
