package com.hamsoft.restapi.controller;

import com.hamsoft.restapi.domain.Company;
import com.hamsoft.restapi.exception.ResourceNotFoundException;
import com.hamsoft.restapi.service.CompanyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Companies")
@RestController
@RequestMapping(APIName.COMPANIES)
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public Company createCompany(@Valid @RequestBody Company company) {

        return companyService.saveCompany(company);
    }

    @GetMapping
    public List<Company> getAllCompany() {
        return companyService.listAllCompanies();
    }


    @GetMapping("{id}")
    public Company getCompany(@PathVariable(value = "id") Long companyId ){

       return companyService.getCompanyById(companyId)
               .orElseThrow(()->new ResourceNotFoundException("Company","id",companyId));
    }

    @PutMapping("{id}")
    public  Company updateCompany(@PathVariable(value = "id") Long companyId , @Valid @RequestBody Company companyRequest){
        return  companyService.getCompanyById(companyId).map(
                company -> {
                    company.setName(companyRequest.getName());
                    return  companyService.saveCompany(company);
                }

        ).orElseThrow(()->new ResourceNotFoundException("Company","id",companyId));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable(value = "id") Long companyId) {
        return companyService.getCompanyById(companyId).map(company -> {
            companyService.deleteCompany(company.getId());
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Company","id",companyId));
    }






}
