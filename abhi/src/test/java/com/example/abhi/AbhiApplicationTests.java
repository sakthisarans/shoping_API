package com.example.abhi;

//import com.example.abhi.dbconnect.accesstokendb;
import com.example.abhi.accesstoken.accesstoken;
import com.example.abhi.accesstoken.accesstokens;
import com.example.abhi.dbconnect.accesstokendb;
import com.example.abhi.dbconnect.mongod;
import com.example.abhi.dbconnect.signin;
import com.example.abhi.signinbody.signininputdata;
import com.example.abhi.signinbody.singnininputdatatest;
import com.example.abhi.singnupinputbody.userdata;
import com.example.abhi.singnupinputbody.userdatatest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.source.tree.ModuleTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(endpointcontroller.class)
@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AbhiApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	signin sin;
	@MockBean
	mongod mon;
	@MockBean
	accesstokendb ac;
	@Autowired
	ObjectMapper objectMapper;
	@BeforeEach
	public void setup(){
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void signup() throws Exception{
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");
		userdatatest ud=new userdatatest();
		List<String> s=new ArrayList<>();
		s.add("user");
		ud.setFirstName("sakthisaran");ud.setLastName("S");ud.setRequestType("SignUp");ud.setPhoneNumber("8925317177");
		ud.setEmail("sakthisaransss51@gmail.com");ud.setUserType(s);ud.setSiteId("5ebb27df-a2e6-40d8-95a2-6da486737327");
		ud.setCustomerId("dcf1687d-c466-4992-9dcf-b6ea3ab87007");ud.setPassword("qwertyuiop#S5");ud.setAccessCode(null);ud.setSource("ACCESS-CODE");
		ud.setCountryCode("+91");ud.setDealsAndDiscountsNewsLetters(false);ud.setAcceptAgreement(true);ud.setMultiChannelSource("ACCESS-CODE");
		ud.setDontHaveAccessCode(true);ud.setSourceFrom("web");
		System.out.println(ud);
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");
		try {
			userdata ud1=objectMapper.convertValue(ud, userdata.class);
			Mockito.when(mon.save(any())).thenReturn(ud1);
			Mockito.when(mon.finfindByemail("sakthisaransss51@gmail.com")).thenReturn(null);
		}catch (Exception ex){
			System.out.println("when");
			System.out.println(ex);
		}

		System.out.println(objectMapper.writeValueAsString(ud));
			mockMvc.perform(MockMvcRequestBuilders
							.post("/endpoint/")
							.content(objectMapper.writeValueAsString(ud))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated());
	}
	@Test
	void signupexistinguser() throws Exception{
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");
		userdatatest ud=new userdatatest();
		List<String> s=new ArrayList<>();
		s.add("user");
		ud.setFirstName("sakthisaran");ud.setLastName("S");ud.setRequestType("SignUp");ud.setPhoneNumber("8925317177");
		ud.setEmail("sakthisaransss51@gmail.com");ud.setUserType(s);ud.setSiteId("5ebb27df-a2e6-40d8-95a2-6da486737327");
		ud.setCustomerId("dcf1687d-c466-4992-9dcf-b6ea3ab87007");ud.setPassword("qwertyuiop#S5");ud.setAccessCode(null);ud.setSource("ACCESS-CODE");
		ud.setCountryCode("+91");ud.setDealsAndDiscountsNewsLetters(false);ud.setAcceptAgreement(true);ud.setMultiChannelSource("ACCESS-CODE");
		ud.setDontHaveAccessCode(true);ud.setSourceFrom("web");
		System.out.println(ud);
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");
		try {
			userdata ud1=objectMapper.convertValue(ud, userdata.class);
			Mockito.when(mon.save(any())).thenReturn(ud1);
			Mockito.when(mon.finfindByemail("sakthisaransss51@gmail.com")).thenReturn(ud1);
		}catch (Exception ex){
			System.out.println("when");
			System.out.println(ex);
		}

		System.out.println(objectMapper.writeValueAsString(ud));
		mockMvc.perform(MockMvcRequestBuilders
						.post("/endpoint/")
						.content(objectMapper.writeValueAsString(ud))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAlreadyReported());
	}
	@Test
	void signin() throws Exception{
		String ob="{\"siteId\":\"5ebb27df-a2e6-40d8-95a2-6da486737327\",\"requestType\":\"SignIn\",\"email\":\"sakthisaransss51@gmail.com\",\"password\":\"qwertyuiop#S5\",\"deviceFingerPrint\":{\"deviceFingerPrintId\":\"95ae54c843da5c42e72c2d525ab6411bMac/iOSChrome108.0.0.0\",\"browserInfo\":{\"browserName\":\"Chrome\",\"fullVersion\":\"117.0.0.2\",\"majorVersion\":\"117\",\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36\",\"url\":\"https://app.mondee.com/admin/enroll/login\",\"ipAddress\":\"152.58.232.127\",\"os\":\"Mac/iOS\",\"deviceName\":\"Desktop_MAC\",\"deviceType\":\"Web\"},\"ipAddress\":\"152.58.232.127\",\"fingerPrintId\":\"db6ec3639bdf6d72e2ff51f0bf5e8dba\"},\"domain\":\"app.mondee.com\"}";
		singnininputdatatest ud=objectMapper.readValue(ob,singnininputdatatest.class);
		System.out.println(ud);
		userdata ud1=objectMapper.convertValue(ud, userdata.class);
		ud1.setId("1234567890");
		Mockito.when(mon.finfindByemail("sakthisaransss51@gmail.com")).thenReturn(ud1);
		Mockito.when(sin.findByid(any())).thenReturn(null);
		Mockito.when(ac.findByaccesstoken(any())).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
						.post("/endpoint/")
						.content(objectMapper.writeValueAsString(ud))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
	@Test
	void signinwithaccesstoken() throws Exception{
		accesstoken ac0=new accesstoken();
		ac0.setAccesstoken("ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz");
		ac0.setExpiry_date("2023-11-20T17:47:56.025756");
		ac0.setDate_of_creation("2023-11-14T17:47:56.025756");
		ac0.setUsername("test");
		accesstokens ac1=new accesstokens();
		ac1.setToken(ac0);
		List<String> l=new ArrayList<>();
		l.add("member");
		ac1.setRoll(l);
		String ob="{\"siteId\":\"5ebb27df-a2e6-40d8-95a2-6da486737327\",\"requestType\":\"SignIn\",\"email\":\"sakthisaransss51@gmail.com\",\"password\":\"qwertyuiop#S5\",\"deviceFingerPrint\":{\"deviceFingerPrintId\":\"95ae54c843da5c42e72c2d525ab6411bMac/iOSChrome108.0.0.0\",\"browserInfo\":{\"browserName\":\"Chrome\",\"fullVersion\":\"117.0.0.2\",\"majorVersion\":\"117\",\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36\",\"url\":\"https://app.mondee.com/admin/enroll/login\",\"ipAddress\":\"152.58.232.127\",\"os\":\"Mac/iOS\",\"deviceName\":\"Desktop_MAC\",\"deviceType\":\"Web\"},\"ipAddress\":\"152.58.232.127\",\"fingerPrintId\":\"db6ec3639bdf6d72e2ff51f0bf5e8dba\"},\"domain\":\"app.mondee.com\"}";
		singnininputdatatest ud=objectMapper.readValue(ob,singnininputdatatest.class);
		System.out.println(ud);
		userdata ud1=objectMapper.convertValue(ud, userdata.class);
		ud1.setId("1234567890");
		Mockito.when(mon.finfindByemail("sakthisaransss51@gmail.com")).thenReturn(ud1);
		Mockito.when(sin.findByid(any())).thenReturn(null);
		Mockito.when(ac.findByid("1234567890")).thenReturn(ac1);
		Mockito.when(ac.findByaccesstoken(any())).thenReturn(ac1);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/endpoint/")
				.content(objectMapper.writeValueAsString(ud))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
	@Test
	void signinunregistereduser() throws Exception{
		String ob="{\"siteId\":\"5ebb27df-a2e6-40d8-95a2-6da486737327\",\"requestType\":\"SignIn\",\"email\":\"sakthisaransss51@gmail.com\",\"password\":\"qwertyuiop#S5\",\"deviceFingerPrint\":{\"deviceFingerPrintId\":\"95ae54c843da5c42e72c2d525ab6411bMac/iOSChrome108.0.0.0\",\"browserInfo\":{\"browserName\":\"Chrome\",\"fullVersion\":\"117.0.0.2\",\"majorVersion\":\"117\",\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36\",\"url\":\"https://app.mondee.com/admin/enroll/login\",\"ipAddress\":\"152.58.232.127\",\"os\":\"Mac/iOS\",\"deviceName\":\"Desktop_MAC\",\"deviceType\":\"Web\"},\"ipAddress\":\"152.58.232.127\",\"fingerPrintId\":\"db6ec3639bdf6d72e2ff51f0bf5e8dba\"},\"domain\":\"app.mondee.com\"}";
		singnininputdatatest ud=objectMapper.readValue(ob,singnininputdatatest.class);
		System.out.println(ud);
		userdata ud1=objectMapper.convertValue(ud, userdata.class);
		Mockito.when(mon.finfindByemail("sakthisaransss51@gmail.com")).thenReturn(null);
		Mockito.when(sin.findByid(any())).thenReturn(null);
		Mockito.when(ac.findByaccesstoken(any())).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/endpoint/")
				.content(objectMapper.writeValueAsString(ud))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}
	@Test
	void signout() throws Exception{
		accesstoken ac0=new accesstoken();
		ac0.setAccesstoken("1234567890");
		ac0.setExpiry_date("2023-11-20T17:47:56.025756");
		ac0.setDate_of_creation("2023-11-14T17:47:56.025756");
		ac0.setUsername("test");
		accesstokens ac1=new accesstokens();
		ac1.setToken(ac0);
		List<String> l=new ArrayList<>();
		l.add("member");
		ac1.setRoll(l);
		Mockito.when(ac.findByaccesstoken("1234567890")).thenReturn(ac1);
		mockMvc.perform(MockMvcRequestBuilders.get("/endpoint/signout").param("accesstoken","1234567890")).andExpect(status().isOk());
	}
	@Test
	void signoutinvalid() throws Exception{
		Mockito.when(ac.findByaccesstoken(any())).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/endpoint/signout").param("accesstoken","1234567890")).andExpect(status().isOk());
	}

}
