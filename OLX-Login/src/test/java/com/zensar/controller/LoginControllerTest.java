package com.zensar.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zensar.dto.User;
import com.zensar.service.LoginService;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	LoginService loginService;

//	@Test
//	public void testRegisterUser() {
//		User userlist = new User();
//		userlist.setFirstName("pavan");
//
//		when(this.loginService.registerUser(userlist)).thenReturn(userlist);
//
//		MvcResult mvcResult = this.mockMvc.perform(post("localhost:9000/olx/login/user").contentType("application/json")
//				.content(objectMapper.writeValueAsString(userlist)).andExpect(Status.OK)
//				.andExpect(content().string(containsString("pavan"))).andReturn());
//
//		String response = mvcResult.getResponse().getContentAsString();
//		assertEquals(response.contains("pavan"), true);
//
//	}
	
	
	
	
	

}
