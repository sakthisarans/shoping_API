package com.example.shopping;

import com.example.shopping.productdb.productdb;
import com.example.shopping.productdb.tokendb;
import com.example.shopping.products.multiproduct;
import com.example.shopping.products.products;
import com.example.shopping.reducecount.reducecount;
import com.example.shopping.token.accesstokens;
import com.example.shopping.token.token;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ShoppingApplicationTests {
	@MockBean
	private tokendb tokn;
	@Autowired
	ObjectMapper objectMapper;
	@MockBean
	productdb proddb;
	@Autowired
	private MockMvc mvc;
	@Test
	void newtest() throws Exception {
		ResultActions rm = mvc.perform(get("/product/getProducts").contentType(MediaType.APPLICATION_JSON));
		rm.andExpect(status().isBadRequest());
		rm.andExpect(content().string(containsString("access tokendb is mandidatory")));
	}
	accesstokens gettoken(){
		token test=new token();
		accesstokens ac=new accesstokens();
		test.setAccesstoken("ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz");
		test.setDate_of_creation("2023-11-02T19:11:45.093643400");
		test.setExpiry_date("2030-11-30T19:11:45.094642800");
		test.setUsername("test");
		ac.setToken(test);
		List<String> l=new ArrayList<>();
		l.add("merchant");
		ac.setRoll(l);
		return  ac;
	}
	accesstokens gettoken(boolean b){
		token test=new token();
		accesstokens ac=new accesstokens();
		test.setAccesstoken("ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz");
		test.setDate_of_creation("2023-11-02T19:11:45.093643400");
		test.setExpiry_date("2030-11-30T19:11:45.094642800");
		test.setUsername("test");
		ac.setToken(test);
		List<String> l=new ArrayList<>();
		l.add("member");
		ac.setRoll(l);
		return  ac;
	}
	@Test

	void getprod() throws Exception {
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		String s="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22,\"_class\":\"com.example.shopping.products.products\"}";
		products temp=objectMapper.readValue(s, products.class);
		List<products> temp1=new ArrayList<>();
		temp1.add(temp);temp1.add(temp);
		Mockito.when(proddb.findAll()).thenReturn(temp1);

			ResultActions rm = mvc.perform(get("/product/getProducts").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON));
			rm.andExpect(status().isOk());
	}
	@Test
	void getprodinvalidtoken() throws Exception {
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(null);
		String s="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22,\"_class\":\"com.example.shopping.products.products\"}";
		products temp=objectMapper.readValue(s, products.class);
		List<products> temp1=new ArrayList<>();
		temp1.add(temp);temp1.add(temp);
		Mockito.when(proddb.findAll()).thenReturn(temp1);

		ResultActions rm = mvc.perform(get("/product/getProducts").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON));
		rm.andExpect(status().isBadRequest());
	}
	@Test

	void addproduct() throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(null);
		Mockito.when(proddb.save(any())).thenReturn(temp);
		mvc.perform(post("/product/addProduct").param("accesstoken", t).content(objectMapper.writeValueAsString(temp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void addproductmissindfields() throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(null);
		Mockito.when(proddb.save(any())).thenReturn(temp);
		mvc.perform(post("/product/addProduct").param("accesstoken", t).content(objectMapper.writeValueAsString(temp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test

	void addproductunauthuser() throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken(true));
		Mockito.when(proddb.findByproductid("1113")).thenReturn(null);
		Mockito.when(proddb.save(any())).thenReturn(temp);
		mvc.perform(post("/product/addProduct").param("accesstoken", t).content(objectMapper.writeValueAsString(temp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}
	@Test
	void addproductexist() throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(temp);
		Mockito.when(proddb.save(any())).thenReturn(temp);
		mvc.perform(post("/product/addProduct").param("accesstoken", t).content(objectMapper.writeValueAsString(temp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isFound());
	}
	@Test
	void addproducts() throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		multiproduct mp=new multiproduct();
		List<products> l=new ArrayList<>();
		l.add(temp);l.add(temp);
		mp.setProducts(l);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(null);
		Mockito.when(proddb.save(any())).thenReturn(temp);
		mvc.perform(post("/product/addProducts").param("accesstoken", t).content(objectMapper.writeValueAsString(mp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void addproductsmissingvalue() throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		multiproduct mp=new multiproduct();
		List<products> l=new ArrayList<>();
		l.add(temp);l.add(temp);
		mp.setProducts(l);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(null);
		Mockito.when(proddb.save(any())).thenReturn(temp);
		mvc.perform(post("/product/addProducts").param("accesstoken", t).content(objectMapper.writeValueAsString(mp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void addproductsexist() throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		multiproduct mp=new multiproduct();
		List<products> l=new ArrayList<>();
		l.add(temp);l.add(temp);
		mp.setProducts(l);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(temp);
		Mockito.when(proddb.save(any())).thenReturn(temp);
		mvc.perform(post("/product/addProducts").param("accesstoken", t).content(objectMapper.writeValueAsString(mp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isFound());
	}

	@Test
	void addproductsunauthuser() throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		multiproduct mp=new multiproduct();
		List<products> l=new ArrayList<>();
		l.add(temp);l.add(temp);
		mp.setProducts(l);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken(true));
		Mockito.when(proddb.findByproductid("1113")).thenReturn(null);
		Mockito.when(proddb.save(any())).thenReturn(temp);
		mvc.perform(post("/product/addProducts").param("accesstoken", t).content(objectMapper.writeValueAsString(mp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}
	@Test
	void getproducts()throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		List<products> l=new ArrayList<>();
		l.add(temp);l.add(temp);
		Mockito.when(proddb.findAll()).thenReturn(l);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		mvc.perform(get("/product/getProducts").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void getproductsinvalidaccesstoken()throws Exception{
		String prod="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		products temp=objectMapper.readValue(prod, products.class);
		List<products> l=new ArrayList<>();
		l.add(temp);l.add(temp);
		Mockito.when(proddb.findAll()).thenReturn(l);
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(null);
		mvc.perform(get("/product/getProducts").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void getproductlist()throws Exception{
		String prod1113="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String prod1114="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1114\",\"category\":\"Library Meeting Rooms\"}";
		products temp=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1114, products.class);
		List<products> ret=new ArrayList<>();
		ret.add(temp);ret.add(temp1);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		List<String> ids=new ArrayList<>();
		ids.add("1113");ids.add("1114");
		Mockito.when(proddb.findByproductid(ids)).thenReturn(ret);
		mvc.perform(post("/product/getProductlist").param("accesstoken", t).content(objectMapper.writeValueAsString(ids)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void getproductlistinvalidtoken()throws Exception{
		String prod1113="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String prod1114="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1114\",\"category\":\"Library Meeting Rooms\"}";
		products temp=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1114, products.class);
		List<products> ret=new ArrayList<>();
		ret.add(temp);ret.add(temp1);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(null);
		List<String> ids=new ArrayList<>();
		ids.add("1113");ids.add("1114");
		Mockito.when(proddb.findByproductid(ids)).thenReturn(ret);
		mvc.perform(post("/product/getProductlist").param("accesstoken", t).content(objectMapper.writeValueAsString(ids)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void reducecount() throws Exception{
		String prod1113="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String prod1114="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1114\",\"category\":\"Library Meeting Rooms\"}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1114, products.class);
		List<products> ret=new ArrayList<>();
		ret.add(temp0);ret.add(temp1);
		List<reducecount> temp=new ArrayList<>();
		reducecount t0=new reducecount();
		reducecount t1=new reducecount();
		t0.setProductid("1113");t1.setProductid("1114");
		t0.setSellerid("123456");t1.setSellerid("123456");
		t0.setCount(2);t1.setCount(2);
		temp.add(t0);temp.add(t1);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		List<String> ids=new ArrayList<>();ids.add("1113");ids.add("1114");
		Mockito.when(proddb.findByproductid(ids)).thenReturn(ret);
		Mockito.when(proddb.saveAll(any())).thenReturn(ret);
		mvc.perform(post("/product/reducecount").param("accesstoken", t).content(objectMapper.writeValueAsString(temp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void reducecountinvalidtoken() throws Exception{
		String prod1113="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"category\":\"Library Meeting Rooms\"}";
		String prod1114="{\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"description\":\"\",\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"n\":1,\"name\":\"S10\",\"currency\":\"AED\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1114\",\"category\":\"Library Meeting Rooms\"}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1114, products.class);
		List<products> ret=new ArrayList<>();
		ret.add(temp0);ret.add(temp1);
		List<reducecount> temp=new ArrayList<>();
		reducecount t0=new reducecount();
		reducecount t1=new reducecount();
		t0.setProductid("1113");t1.setProductid("1114");
		t0.setSellerid("123456");t1.setSellerid("123456");
		t0.setCount(2);t1.setCount(2);
		temp.add(t0);temp.add(t1);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(null);
		List<String> ids=new ArrayList<>();ids.add("1113");ids.add("1114");
		Mockito.when(proddb.findByproductid(ids)).thenReturn(ret);
		Mockito.when(proddb.saveAll(any())).thenReturn(ret);
		mvc.perform(post("/product/reducecount").param("accesstoken", t).content(objectMapper.writeValueAsString(temp)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void checkdtock()throws Exception{
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		String prod1114="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:08:15.938871500\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1114\",\"stockcount\":0}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1114, products.class);
		System.out.println(temp0);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(temp0);
		Mockito.when(proddb.findByproductid("1114")).thenReturn(temp1);
		mvc.perform(get("/product/stockcount/1113").param("accesstoken", "password").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mvc.perform(get("/product/stockcount/1114").param("accesstoken", "password").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void checkdtockinvalidtoken()throws Exception{
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		String prod1114="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:08:15.938871500\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1114\",\"stockcount\":0}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1114, products.class);
		System.out.println(temp0);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(temp0);
		Mockito.when(proddb.findByproductid("1114")).thenReturn(temp1);
		mvc.perform(get("/product/stockcount/1113").param("accesstoken", "passwords").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
//		mvc.perform(get("/product/stockcount/1114").param("accesstoken", "password").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void updateprod()throws Exception{
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		String prod1114="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:08:15.938871500\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1114\",\"stockcount\":0}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1114, products.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String t1="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz1";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(tokn.findByaccesstoken(t1)).thenReturn(gettoken(true));
		Mockito.when(proddb.findByproductid("1113")).thenReturn(temp0);
		Mockito.when(proddb.findByproductid("1114")).thenReturn(temp1);
		Mockito.when(proddb.save(any())).thenReturn(any());
		mvc.perform(post("/product/updateProduct/1113").param("accesstoken", t).content(objectMapper.writeValueAsString(temp0)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mvc.perform(post("/product/updateProduct/1114").param("accesstoken", t1).content(objectMapper.writeValueAsString(temp1)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}
	@Test
	void updateprodinvalidtoken()throws Exception{
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
    	products temp0=objectMapper.readValue(prod1113, products.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(null);
		Mockito.when(proddb.findByproductid("1113")).thenReturn(temp0);
		Mockito.when(proddb.save(any())).thenReturn(any());
		mvc.perform(post("/product/updateProduct/1113").param("accesstoken", t).content(objectMapper.writeValueAsString(temp0)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void getProduct()throws Exception{
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(gettoken());
		Mockito.when(proddb.findByproductid("1113")).thenReturn(temp0);
		Mockito.when(proddb.findByproductid("1114")).thenReturn(null);
		mvc.perform(get("/product/getProduct").param("accesstoken", t).param("productid","1113").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mvc.perform(get("/product/getProduct").param("accesstoken", t).param("productid","1114").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void getProductinvalidtoken()throws Exception{
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(tokn.findByaccesstoken(t)).thenReturn(null);
		Mockito.when(proddb.findByproductid("1113")).thenReturn(temp0);
		mvc.perform(get("/product/getProduct").param("accesstoken", t).param("productid","1113").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		}
}
