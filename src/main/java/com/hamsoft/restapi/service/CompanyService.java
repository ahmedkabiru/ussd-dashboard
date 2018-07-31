package com.hamsoft.restapi.service;

import com.hamsoft.restapi.domain.Company;

import java.util.Optional;

public interface CompanyService {

    Iterable<Company> listAllCompanies();

    Optional<Company> getCompanyById(Long id);

    Company saveCompany(Company company);

    void deleteCompany(Long id);
}
