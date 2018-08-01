package com.hamsoft.restapi.controller;

import com.hamsoft.restapi.domain.Company;
import com.hamsoft.restapi.exception.ResourceNotFoundException;
import com.hamsoft.restapi.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CompanyController {


    private CompanyService companyService;


    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping("/companies")
    public Company createCompany(@Valid @RequestBody Company company) {

        return companyService.saveCompany(company);
    }

    @GetMapping("/companies")
    public Iterable<Company> getAllCompany() {
        return companyService.listAllCompanies();
    }


    @GetMapping("/companies/{id}")
    public Company getCompany(@PathVariable(value = "id") Long companyId ){

       return companyService.getCompanyById(companyId)
               .orElseThrow(()->new ResourceNotFoundException("Company","id",companyId));
    }

    @PutMapping("/companies/{id}")
    public  Company updateCompany(@PathVariable(value = "id") Long companyId , @Valid @RequestBody Company companyRequest){
        return  companyService.getCompanyById(companyId).map(
                company -> {
                    company.setName(companyRequest.getName());
                    return  companyService.saveCompany(company);
                }

        ).orElseThrow(()->new ResourceNotFoundException("Company","id",companyId));

    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(value = "id") Long companyId) {
        return companyService.getCompanyById(companyId).map(company -> {
            companyService.deleteCompany(company.getId());
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Company","id",companyId));
    }






}
