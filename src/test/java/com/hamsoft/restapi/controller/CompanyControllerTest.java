package com.hamsoft.restapi.controller;


import com.hamsoft.restapi.domain.Company;
import com.hamsoft.restapi.repository.CompanyRepository;
import com.hamsoft.restapi.service.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
////@WebMvcTest(controllers = CompanyController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class CompanyControllerTest {

//    @LocalServerPort
//    private int port;


//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CompanyRepository companyRepository;
//
//    @MockBean
//    private CompanyService companyService;
//
//
//    @Test
//    public void whenFindByName_thenReturnEmployee() {
//        Company company = new Company("AIRTEL");
//
//    }
//    @Test
//    public void shouldReturnHelloWorld() throws Exception {
//      //  when(service.greet()).thenReturn("Hello Mock");
//       // Company company = new Company();
//        //List<String> list= Arrays.asList("Ahmed","kabiru");
//        //when(companyService.listAllCompanies()).thenReturn(list);
//        Company company = new Company();
//        company.setName("MTN");
//        List<Company> allCompanies = Collections.singletonList(company);
//        given(companyService.listAllCompanies()).willReturn(allCompanies);
//        this.mockMvc.perform(get("/api/companies"))
//                .andDo(print())
//
//                //.andExpect(content().string(""))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"));
//    }


}