package com.hamsoft.restapi;

import com.hamsoft.restapi.domain.Category;
import com.hamsoft.restapi.domain.Company;
import com.hamsoft.restapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class RestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@Autowired
	CompanyRepository companyRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		// create a company
//		Company company = new Company("Company A");
//
//		//create Category
//		Category category1 = new Category("MTN");
//		category1.setCompany(company);
//
//		Category category2 = new Category("GLO");
//		category2.setCompany(company);
//
//		// add category in the company
//
//		company.getCategories().add(category1);
//		company.getCategories().add(category2);
//
//		companyRepository.save(company);

	}
}
