package com.example.useractivity;

import com.example.useractivity.dbconnect.Cartdb;
import com.example.useractivity.cart.cart;
import com.example.useractivity.dbconnect.Orderdata;
import com.example.useractivity.dbconnect.morderdb;
import com.example.useractivity.dbconnect.tokendb;
import com.example.useractivity.merchantorder.merchantorder;
import com.example.useractivity.merchantorder.merchantorderlist;
import com.example.useractivity.product.products;
import com.example.useractivity.service.getsellerids;
import com.example.useractivity.shoppinghistory.product;
import com.example.useractivity.shoppinghistory.shopping;
import com.example.useractivity.token.accesstokens;
import com.example.useractivity.token.token;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class UseractivityApplicationTests {
	@MockBean
	getsellerids gs;
	@MockBean
	Orderdata od;
	@Autowired
	MockMvc mvc;
	@MockBean
	morderdb md;
	@MockBean
	tokendb db;
//	@MockBean
//	userController uc;
	@MockBean
	Cartdb cdb;
	@Autowired
	ObjectMapper objectMapper;
	@Mock
	RestTemplate rt;

	accesstokens gettoken(){
		token test=new token();
		accesstokens ac=new accesstokens();
		test.setAccesstoken("ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz");
		test.setDate_of_creation("2023-11-02T19:11:45.093643400");
		test.setExpiry_date("2023-11-30T19:11:45.094642800");
		test.setUsername("test");
		ac.setToken(test);
		List<String> l=new ArrayList<>();
		l.add("member");
		ac.setRoll(l);
		return  ac;
	}
	@Test
	void addtocart()throws Exception {

		//cart is empty

		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String id="1113";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(any())).thenReturn(null);
		Mockito.when(gs.checkstock(id))
				.thenReturn(true);
		Mockito.when(cdb.save(any())).thenReturn(any());
		mvc.perform(get("/user/addtocart").param("accesstoken", t).param("productid",id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

	}
	@Test
	void addtocartexistingcart()throws Exception {
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String id="1113";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		System.out.println(c);
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(gs.checkstock(id))
				.thenReturn(true);
		Mockito.when(cdb.save(any())).thenReturn(any());
		mvc.perform(get("/user/addtocart").param("accesstoken", t).param("productid",id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void addtocartcustomname()throws Exception {
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String id="1113";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		System.out.println(c);
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(gs.checkstock(id))
				.thenReturn(true);
		Mockito.when(cdb.save(any())).thenReturn(any());
		mvc.perform(get("/user/addtocart").param("accesstoken", t).param("productid",id).param("cartname","test").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void addtocartinvalidtoken()throws Exception {
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String id="1113";
		Mockito.when(db.findbytoken(t)).thenReturn(null);
		System.out.println(c);
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(gs.checkstock(id))
				.thenReturn(true);
		Mockito.when(cdb.save(any())).thenReturn(any());
		mvc.perform(get("/user/addtocart").param("accesstoken", t).param("productid",id).param("cartname","test").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void clearcart()throws Exception{
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String id="1113";
		String cartname="shopping";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(cdb.save(any())).thenReturn(any());
		mvc.perform(get("/user/clearcart").param("accesstoken", t).param("cartname",cartname).param("removeitem","false").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void clearcartmissingcary()throws Exception{
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String id="1113";
		String cartname="test";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(any())).thenReturn(null);
		Mockito.when(cdb.save(any())).thenReturn(any());
		mvc.perform(get("/user/clearcart").param("accesstoken", t).param("cartname",cartname).param("removeitem","false").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void clearcartremoveitem()throws Exception{
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String id="1113";
		String cartname="test";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(cdb.save(any())).thenReturn(any());
		mvc.perform(get("/user/clearcart").param("accesstoken", t).param("cartname",cartname).param("removeitem","true").param("productid","1113").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void clearcartinvalidtoken()throws Exception{
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String id="1113";
		String cartname="test";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(null);
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(cdb.save(any())).thenReturn(any());
		mvc.perform(get("/user/clearcart").param("accesstoken", t).param("cartname",cartname).param("removeitem","true").param("productid","1113").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void getcart()throws Exception{
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1},{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"exp\",\"prodictid\":\"1112\",\"count\":2}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String cartname="test";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		mvc.perform(get("/user/getcart").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(cart));

	}
	@Test
	void getcartwithname()throws Exception{
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1},{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"exp\",\"prodictid\":\"1112\",\"count\":2}]}";
		String test="{\"cartitems\":[{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String cartname="test";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		mvc.perform(get("/user/getcart").param("accesstoken", t).param("cartname",cartname).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(test));
	}
	@Test
	void getcartinvalidtoken()throws Exception{
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1},{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"exp\",\"prodictid\":\"1112\",\"count\":2}]}";
		String test="{\"cartitems\":[{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2}]}";
		cart c=objectMapper.readValue(cart,cart.class);
		String cartname="test";
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(null);
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		mvc.perform(get("/user/getcart").param("accesstoken", t).param("cartname",cartname).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	void canellorder()throws Exception{
		String order="{\"userid\":\"654c8aae74cdad28bcfa7e97\",\"product\":[{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2}],\"orderid\":\"bTPrOv2Vqmaa\",\"update\":[\"Order placed successfully\"],\"cancel\":false,\"paymentinitiative\":true,\"paymentcomplete\":false,\"orderconfirm\":false,\"addressid\":0}";
		shopping sh=objectMapper.readValue(order, shopping.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(od.findByOrderid("bTPrOv2Vqmaa")).thenReturn(sh);
		mvc.perform(get("/user/cancelorder/bTPrOv2Vqmaa").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void canellorderunknown()throws Exception{
		String order="{\"userid\":\"654c8aae74cdad28bcfa7e97\",\"product\":[{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2}],\"orderid\":\"bTPrOv2Vqmaa\",\"update\":[\"Order placed successfully\"],\"cancel\":false,\"paymentinitiative\":true,\"paymentcomplete\":false,\"orderconfirm\":false,\"addressid\":0}";
		shopping sh=objectMapper.readValue(order, shopping.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(od.findByOrderid(any())).thenReturn(null);
		mvc.perform(get("/user/cancelorder/bTPrOv2Vqmaa").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void canellorderinvalidtoken()throws Exception{
		String order="{\"userid\":\"654c8aae74cdad28bcfa7e97\",\"product\":[{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2}],\"orderid\":\"bTPrOv2Vqmaa\",\"update\":[\"Order placed successfully\"],\"cancel\":false,\"paymentinitiative\":true,\"paymentcomplete\":false,\"orderconfirm\":false,\"addressid\":0}";
		shopping sh=objectMapper.readValue(order, shopping.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(null);
		Mockito.when(od.findByOrderid(any())).thenReturn(null);
		mvc.perform(get("/user/cancelorder/12345").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void getorders()throws Exception{
		String order="{\"userid\":\"654c8aae74cdad28bcfa7e97\",\"product\":[{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2}],\"orderid\":\"bTPrOv2Vqmaa\",\"update\":[\"Order placed successfully\"],\"cancel\":false,\"paymentinitiative\":true,\"paymentcomplete\":false,\"orderconfirm\":false,\"addressid\":0}";
		shopping sh=objectMapper.readValue(order, shopping.class);
		List<shopping> sh1=new ArrayList<>();
		sh1.add(sh);sh1.add(sh);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(od.findByUserid(any())).thenReturn(sh1);
		mvc.perform(get("/user/getorders").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void getordersnoorder()throws Exception{
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(od.findByUserid(any())).thenReturn(null);
		mvc.perform(get("/user/getorders").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void getordersinvalidtoken()throws Exception{
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(null);
		Mockito.when(od.findByUserid(any())).thenReturn(null);
		mvc.perform(get("/user/getorders").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	@Test
	void getorder()throws Exception{
		String order="{\"userid\":\"654c8aae74cdad28bcfa7e97\",\"product\":[{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2}],\"orderid\":\"bTPrOv2Vqmaa\",\"update\":[\"Order placed successfully\"],\"cancel\":false,\"paymentinitiative\":true,\"paymentcomplete\":false,\"orderconfirm\":false,\"addressid\":0}";
		shopping sh=objectMapper.readValue(order, shopping.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(od.findByOrderid(any())).thenReturn(sh);
		mvc.perform(get("/user/getorder/bTPrOv2Vqmaa").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void getordernotfound()throws Exception{
		String order="{\"userid\":\"654c8aae74cdad28bcfa7e97\",\"product\":[{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2}],\"orderid\":\"bTPrOv2Vqmaa\",\"update\":[\"Order placed successfully\"],\"cancel\":false,\"paymentinitiative\":true,\"paymentcomplete\":false,\"orderconfirm\":false,\"addressid\":0}";
		shopping sh=objectMapper.readValue(order, shopping.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(od.findByOrderid(any())).thenReturn(null);
		mvc.perform(get("/user/getorder/bTPrOv2Vqmaa").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void getorderinvalidtoken()throws Exception{
		String order="{\"userid\":\"654c8aae74cdad28bcfa7e97\",\"product\":[{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2}],\"orderid\":\"bTPrOv2Vqmaa\",\"update\":[\"Order placed successfully\"],\"cancel\":false,\"paymentinitiative\":true,\"paymentcomplete\":false,\"orderconfirm\":false,\"addressid\":0}";
		shopping sh=objectMapper.readValue(order, shopping.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		Mockito.when(db.findbytoken(t)).thenReturn(null);
		Mockito.when(od.findByOrderid(any())).thenReturn(null);
		mvc.perform(get("/user/getorder/bTPrOv2Vqmaa").param("accesstoken", t).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	void checkoutcart() throws Exception{
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1},{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"exp\",\"prodictid\":\"1112\",\"count\":2}]}";
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		String prod1112="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:08:15.938871500\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1112\",\"stockcount\":10}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1112, products.class);
		cart c=objectMapper.readValue(cart,cart.class);
		List<String> l=new ArrayList<>();
		l.add("1113");l.add("1112");
		List<products> nl=new ArrayList<>();
		nl.add(temp0);nl.add(temp1);
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
        Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(md.findByid(any())).thenReturn(null);
//		Mockito.when(md.save(any())).thenReturn(any());
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(gs.getseller(any(),any())).thenReturn(nl);

		product p=new product();
		product p1=new product();
		p.setSellerid("654c8aae74cdad28bcfa7e97");p1.setSellerid("654c8aae74cdad28bcfa7e97");
		p.setProductid("1113");p1.setProductid("1112");
		p.setCount(1);p1.setCount(2);
		List<product> t1=new ArrayList<>();t1.add(p);t1.add(p1);
		Mockito.when(gs.reducecount(any(),any())).thenReturn(HttpStatus.OK);
		String cartname="shopping";
		mvc.perform(get("/user/checkout").param("accesstoken", t).param("cartname",cartname).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}



	@Test
	void checkoutcartwithmerchantorder() throws Exception{
		String merchant="{\"order\":[{\"productid\":\"1113\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":3,\"orderid\":\"e8132FuYpqJf\",\"packed\":false,\"shipped\":false,\"date\":\"1699527995777\",\"cancelled\":false},{\"productid\":\"1356991003871011\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2,\"orderid\":\"e8132FuYpqJg\",\"packed\":false,\"shipped\":false,\"date\":\"1699527995881\",\"cancelled\":false},{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":1,\"orderid\":\"e8132FuYpqJf\",\"packed\":false,\"shipped\":false,\"date\":\"1699527995941\",\"cancelled\":false},{\"productid\":\"1112\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"count\":2,\"orderid\":\"bTPrOv2Vqmaa\",\"packed\":false,\"shipped\":false,\"date\":\"1701240669864\",\"cancelled\":false}]}";
		merchantorderlist mol=objectMapper.readValue(merchant, merchantorderlist.class);
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1},{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"exp\",\"prodictid\":\"1112\",\"count\":2}]}";
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		String prod1112="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:08:15.938871500\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1112\",\"stockcount\":10}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1112, products.class);
		cart c=objectMapper.readValue(cart,cart.class);
		List<String> l=new ArrayList<>();
		l.add("1113");l.add("1112");
		List<products> nl=new ArrayList<>();
		nl.add(temp0);nl.add(temp1);
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(md.findByid(any())).thenReturn(mol);
//		Mockito.when(md.save(any())).thenReturn(any());
		Mockito.when(cdb.findByid(any())).thenReturn(c);
		Mockito.when(gs.getseller(any(),any())).thenReturn(nl);

		product p=new product();
		product p1=new product();
		p.setSellerid("654c8aae74cdad28bcfa7e97");p1.setSellerid("654c8aae74cdad28bcfa7e97");
		p.setProductid("1113");p1.setProductid("1112");
		p.setCount(1);p1.setCount(2);
		List<product> t1=new ArrayList<>();t1.add(p);t1.add(p1);
		Mockito.when(gs.reducecount(any(),any())).thenReturn(HttpStatus.OK);
		String cartname="shopping";
		mvc.perform(get("/user/checkout").param("accesstoken", t).param("cartname",cartname).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	@Test
	void checkoutcartnocart() throws Exception{
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1},{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"exp\",\"prodictid\":\"1112\",\"count\":2}]}";
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		String prod1112="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:08:15.938871500\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1112\",\"stockcount\":10}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1112, products.class);
		cart c=objectMapper.readValue(cart,cart.class);
		List<String> l=new ArrayList<>();
		l.add("1113");l.add("1112");
		List<products> nl=new ArrayList<>();
		nl.add(temp0);nl.add(temp1);
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(t)).thenReturn(null);
		String cartname="shopping";
		mvc.perform(get("/user/checkout").param("accesstoken", t).param("cartname",cartname).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void checkoutcartnocartcname() throws Exception{
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1},{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"exp\",\"prodictid\":\"1112\",\"count\":2}]}";
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		String prod1112="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:08:15.938871500\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1112\",\"stockcount\":10}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1112, products.class);
		cart c=objectMapper.readValue(cart,cart.class);
		List<String> l=new ArrayList<>();
		l.add("1113");l.add("1112");
		List<products> nl=new ArrayList<>();
		nl.add(temp0);nl.add(temp1);
		Mockito.when(db.findbytoken(t)).thenReturn(gettoken());
		Mockito.when(cdb.findByid(t)).thenReturn(c);
		String cartname="shopping";
		mvc.perform(get("/user/checkout").param("accesstoken", t).param("cartname","new").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	@Test
	void checkoutinvalidtoken() throws Exception{
		String t="ZjOWDkRGLFP6uek9z4j_sepAcN3RejSz";
		String cart="{\"cartitems\":[{\"carname\":\"shopping\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"shopping\",\"prodictid\":\"1113\",\"count\":1},{\"carname\":\"test\",\"prodictid\":\"1112\",\"count\":2},{\"carname\":\"exp\",\"prodictid\":\"1112\",\"count\":2}]}";
		String prod1113="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:06:07.871591400\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1113\",\"stockcount\":22}";
		String prod1112="{\"creator\":\"654c8aae74cdad28bcfa7e97\",\"images\":{\"thumbnail1\":\"https://res.cloudinary.com/dddd9bezd/image/upload/v1696486625/Products/25-Under-Graduate-Business-Management_g6c3d4.jpg\"},\"subCategory\":\"Faculty\",\"taxExempt\":false,\"available\":true,\"discountAmount\":0,\"active\":true,\"discountPercentage\":0,\"price\":0,\"description\":\"\",\"name\":\"S10\",\"currency\":\"AED\",\"category\":\"Library Meeting Rooms\",\"sellerid\":\"654c8aae74cdad28bcfa7e97\",\"activationDate\":\"2023-11-09T15:08:15.938871500\",\"attributes\":{\"Slot_Duration\":\"60\",\"Capacity\":\"6\",\"MAXIMUM_SLOT_DUERATION\":\"9\",\"order\":\"20\",\"Location\":\"Building J Second Floor\"},\"productid\":\"1112\",\"stockcount\":10}";
		products temp0=objectMapper.readValue(prod1113, products.class);
		products temp1=objectMapper.readValue(prod1112, products.class);
		cart c=objectMapper.readValue(cart,cart.class);
		List<String> l=new ArrayList<>();
		l.add("1113");l.add("1112");
		List<products> nl=new ArrayList<>();
		nl.add(temp0);nl.add(temp1);
		Mockito.when(db.findbytoken(t)).thenReturn(null);
		Mockito.when(cdb.findByid(t)).thenReturn(c);
		String cartname="shopping";
		mvc.perform(get("/user/checkout").param("accesstoken", t).param("cartname","new").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
}
