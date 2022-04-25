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

@WebMvcTest(AdvertiseController.class)
class AdvertiseControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	AdvertiseController advertiseController;

	@MockBean
	AdvertiseService advertiseService;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void testCreateNewAdvertise() throws Exception {
		Advertise advertise = new Advertise();
		advertise.setTitle("Sofa Sale");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "D78KL");

		when(this.advertiseService.createNewAdvertise(advertise, "D78KL")).thenReturn(advertise);

		MvcResult mvcResult = this.mockMvc
				.perform(post("http://localhost:8003/olx/advertise/postAd").contentType("application/json")
						.content(objectMapper.writeValueAsString(advertise)).headers(httpHeaders))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Sofa"))).andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(response.contains("Sofa"), true);

	}

	@Test
	public void testupdateAdvertise() throws Exception {
		Advertise advertise = new Advertise();
		advertise.setTitle("Sofa Sale");
		HttpHeaders httpHeaders = new HttpHeaders();
		advertise.setTitle("Cricket");
		httpHeaders.set("Authorization", "SG66203");
		when(this.advertiseService.updateAdvertise(advertise, 1, "SG66203")).thenReturn(advertise);
		MvcResult mvcResult = this.mockMvc
				.perform(put("http://localhost:8003/olx/advertise/{id}" + 1).contentType("application/json")
						.content(objectMapper.writeValueAsString(advertise)).headers(httpHeaders))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Cricket"))).andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(response.contains("Cricket"), true);
	}

	@Test
	public void testgetAllAdvertises() throws Exception {
		List advertiseList = new ArrayList<>();
		advertiseList.add(new Advertise());
		advertiseList.add(new Advertise());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "SG66200");
		when(this.advertiseService.getAllAdvertises("SG66200")).thenReturn(advertiseList);
		MvcResult mvcResult = this.mockMvc
				.perform(get("http://localhost:8003/olx/advertise/user/advertise").headers(httpHeaders))
				.andExpect(status().isOk()).andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(response.contains("title"), true);

	}

	@Test
	public void testGetAdvertiseAllById() throws Exception {
		Advertise advertise = new Advertise();
		advertise.setTitle("Sofa Sale");
		HttpHeaders httpHeaders = new HttpHeaders();
		List advertiseList = new ArrayList<>();
		advertiseList.add(advertise);
		httpHeaders.set("Authorization", "SG66200");
		when(this.advertiseService.getAdvertiseAllById(1, "SG66200")).thenReturn(advertiseList);
		MvcResult mvcResult = this.mockMvc
				.perform(get("http://localhost:8003/olx/advertise/user/advertises/{id}" + 1).headers(httpHeaders))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Sofa"))).andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(response.contains("Sofa"), true);
	}

	@Test
	public void testDeleteAdvertiseAllById() throws Exception {
		Advertise advertisefirst = new Advertise();
		advertisefirst.setTitle("Sofa Sale");
		HttpHeaders httpHeaders = new HttpHeaders();
		List advertiseList = new ArrayList<>();
		advertiseList.add(advertisefirst);
		httpHeaders.set("Authorization", "SG66200");
		when(this.advertiseService.deleteAdvertiseById(1, "SG66200")).thenReturn(true);
		MvcResult mvcResult = this.mockMvc
				.perform(delete("http://localhost:8003/olx/advertise/advertise/{id}" + 1).headers(httpHeaders))
				.andExpect(status().isOk()).andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(response.contains("true"), true);
	}

	@Test
	public void testSearchAdvertisesByFilterCriteria() throws Exception {
		List advertiseList = new ArrayList();
		advertiseList.add(new Advertise());
		advertiseList.add(new Advertise());
		when(this.advertiseService.filterAdvertise(null, 0, null, null, null, null, null, null, 0, 10))
				.thenReturn(advertiseList);

		MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:9087/advertises/search/filtercriteria")
				.param("category", "0").param("startIndex", "0")).andExpect(status().isOk()).andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(response.contains("title"), true);
	}

	@Test
	public void testSearchAdvByText() throws Exception {
		Advertise advertise = new Advertise();
		advertise.setTitle("Sofa Sale");
		HttpHeaders httpHeaders = new HttpHeaders();
		List advertiseList = new ArrayList<>();
		advertiseList.add(advertise);
		httpHeaders.set("Authorization", "SG66200");
		when(this.advertiseService.SearchAdvertiseByText("Sofa")).thenReturn(advertiseList);
		MvcResult mvcResult = this.mockMvc
				.perform(get("http://localhost:9087/advertises/search?searchText=Sofa").headers(httpHeaders))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Sofa"))).andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(response.contains("Sofa"), true);
	}

	@Test
	public void testGetAdvertiseById() throws Exception {
		Advertise advertise = new Advertise();
		advertise.setTitle("Sofa Sale");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "AG66200");
		when(this.advertiseService.getAdvertiseById(1, "AG66200")).thenReturn(advertise);
		MvcResult mvcResult = this.mockMvc
				.perform(get("http://localhost:9087/advertises/user/advertise/" + 1).headers(httpHeaders))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Sofa"))).andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(response.contains("Sofa"), true);
	}

}
