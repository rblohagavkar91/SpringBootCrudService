package com.rahul.springboot.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahul.springboot.rest.model.UserInfo;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserDirectoryApplication.class)
@WebAppConfiguration
public class UserServiceTest {

	MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	<T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	
	@Test
	@Order(1) 
	public void test_1() throws Exception {
	   String uri = "/userdetails/addUser";
	   UserInfo user = new UserInfo();
	   user.setUserId("rxxand");
	   user.setEmailId("rahul05@scania.com");
	   user.setDepartment("IJAA");
	   
	   String inputJson = mapToJson(user);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	  
	   ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		assertEquals(content, json);
	}
	
	@Test
	@Order(2) 
	public void test_2() throws Exception {
	   String uri = "/userdetails/rxxand";
	     
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
			      .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   UserInfo userInfo = mapFromJson(content, UserInfo.class);
	   assertEquals(userInfo.getUserId(),"rxxand");
	}
	
	@Test
	@Order(3) 
	public void test_3() throws Exception {
	   String uri = "/userdetails";
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	      .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   UserInfo[] userList = mapFromJson(content, UserInfo[].class);
	   assertTrue(userList.length > 0);
	}
	
	@Test
	@Order(4) 
	public void test_4() throws Exception {
	   String uri = "/userdetails/update/rxxand";
	   UserInfo user = new UserInfo();
	   user.setUserId("rxxand");
	   user.setEmailId("rahullohagavkar@scania.com");
	   user.setDepartment("IJAB");
	   
	   String inputJson = mapToJson(user);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   ObjectMapper mapper = new ObjectMapper();
	   String json = mapper.writeValueAsString(user);
	   assertEquals(content, json);
	}
	
	@Test
	@Order(5) 
	public void test_5() throws Exception {
	   String uri = "/userdetails/delete/rxxand";
	     
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
			      .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   JSONObject jsonObject = new JSONObject(content);
	   boolean delFlag = jsonObject.getBoolean("deleted");
	   
	   assertEquals(delFlag,true);
	}
}
