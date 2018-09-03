package com.hamsoft.restapi.repository;

import com.hamsoft.restapi.domain.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void find_no_company_id_repository_is_empty(){
        List<Company> companies =  companyRepository.findAll();
        assertThat(companies).isNotEmpty();
    }

    @Test
    public void should_save_a_company() {
        Company company = companyRepository.save(new Company("Airtel"));

        assertThat(company).hasFieldOrPropertyWithValue("name","Airtel");
    }

    @Test
    public void should_delete_all_customer() {
//        entityManager.persist(new Co("Jack", "Smith"));
//        entityManager.persist(new Customer("Adam", "Johnson"));

        companyRepository.deleteAll();

        assertThat(companyRepository.findAll()).isEmpty();
    }

    @Test
    public void should_find_all_customers() {
       Company company = new Company("AIRTEL");
        entityManager.persist(company);
        Iterable<Company> companies = companyRepository.findAll();
        assertThat(companies).hasSize(2).contains(company);
    }
    @Test
    public void should_find_customer_by_id() {
        Company company1 = new Company("MTN");
        entityManager.persist(company1);

        Company company2= new Company("AIRTEL");
        entityManager.persist(company2);

        Company foundCustomer =companyRepository.findByName(company2.getName());

        assertThat(foundCustomer).isEqualTo(company2);
    }




}