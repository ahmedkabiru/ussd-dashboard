package com.hamsoft.restapi;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamsoft.restapi.payload.request.LoginRequest;
import com.hamsoft.restapi.payload.request.UserRequest;
import com.hamsoft.restapi.security.CustomUserDetailsService;
import com.hamsoft.restapi.security.jwt.JwtTokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestApiApplicationTests extends  AbstractMvcTest {

	final static String USERNAME = "opeyemi.kabiru@yahoo.com";
	final static String PASSWORD = "password";

//	@Test
//	public void contextLoads() {
//	}

//	private MockMvc mvc;
//
//	@Autowired
//	private WebApplicationContext context;
//
//	@MockBean
//	private AuthenticationManager authenticationManager;
//
//	@MockBean
//	private JwtTokenProvider jwtTokenUtil;
//
//	@MockBean
//	private CustomUserDetailsService jwtUserDetailsService;
//
//	@Before
//	public void setup() {
//		mvc = MockMvcBuilders
//				.webAppContextSetup(context)
//				.apply(springSecurity())
//				.build();
//	}
//
//	@Test
//	//@WithAnonymousUser
//	public void successfulAuthenticationWithAnonymousUser() throws Exception {
//
//	LoginRequest jwtAuthenticationRequest = new LoginRequest();
//	jwtAuthenticationRequest.setUsername("dddd");
//	jwtAuthenticationRequest.setPassword("444444");
//        System.out.println("Auth" + new ObjectMapper().writeValueAsString(jwtAuthenticationRequest));
//		mvc.perform(post("/api/authenticate")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(new ObjectMapper().writeValueAsString(jwtAuthenticationRequest)))
//
//				.andExpect(status().is2xxSuccessful());
//	}

	@Test
	public void userRepositoryWithoutTokenIsForbidden() throws Exception {
		mockMvc.perform(get("/api/users")).andExpect(status().isForbidden());
	}

	@Test
	public void loginOk() throws Exception {
		login("opeyemi.kabiru@yahoo.com", "password")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").exists())
//				.andExpect(jsonPath("$.user.username", equalTo("name")))
//				.andExpect(jsonPath("$.user.password").doesNotExist())
				.andReturn();
	}

	@Test
	public void userRepositoryWithTokenIsAllowed() throws Exception {
		final String token = extractToken(login("opeyemi.kabiru@yahoo.com", "password").andReturn());
		mockMvc.perform(get("/api/users").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

    @Test
	public void loginNok() throws Exception{
		login("opeyemi@yahoo.com","pass").andExpect(status().isUnauthorized());
	}

	@Test
	public void createUser() throws Exception {
		UserRequest userRequest =new UserRequest();
		userRequest.setFirstName("Kabiru");
		userRequest.setLastName("Ahmed");
		userRequest.setUsername("jekii");
		userRequest.setEmail("jekki.kabiru@gmail.com");
		userRequest.setPassword("password");
		mockMvc.perform(
				post("/api/register")
						.content(json(userRequest))
						.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated());
	}





}
