package com.example.useractivity.service;

import com.example.useractivity.dbconnect.Orderdata;
import com.example.useractivity.error.internalerror;
import com.example.useractivity.product.products;
import com.example.useractivity.shoppinghistory.product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
@Configuration
public class getsellerids {
    @Autowired
    private Orderdata order;
    @Value("${endpoint.url.getproduct}")
    private String endpoint;
    @Value("${endpoint.url.reducestock}")
    private String reducestock;
    @Value("${endpoint.url.stockcheck}")
    private String stockcheck;

    public List<products> getseller(List<String> id,String token) throws RuntimeException, JsonProcessingException {
        HttpHeaders hs=new HttpHeaders();
        String url=endpoint+"?accesstoken="+token;
        hs.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate rs = new RestTemplate();
        System.out.println(id);
        ResponseEntity<String> out = rs.exchange(url, HttpMethod.POST, new HttpEntity<>(id, hs), String.class);
        System.out.println(out.getStatusCode());
        System.out.println(out.getBody());
        if(out.getStatusCode().equals(HttpStatus.OK)) {
            ObjectMapper map=new ObjectMapper();
            return map.readValue(out.getBody(), new TypeReference<List<products>>(){});
        }else{
            throw new internalerror("internal server error");
        }
    }
    public String getorderid(){
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 12;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(generatedString);
        if((order.findByOrderid(generatedString)==null)){
            return generatedString;
        }else{
            getorderid();
        }
        return null;
    }
    public HttpStatus reducecount(String token, List<product> prod){
        HttpHeaders hs=new HttpHeaders();
        String url=reducestock+"?accesstoken="+token;
        hs.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate rs = new RestTemplate();
        System.out.println(prod);
        ResponseEntity<String> out = rs.exchange(url, HttpMethod.POST, new HttpEntity<>(prod, hs), String.class);
        System.out.println(out.getStatusCode());
        System.out.println(out.getBody());
        return (HttpStatus) out.getStatusCode();
    }
    public boolean checkstock(String id){
        HttpHeaders hs=new HttpHeaders();
        String url=stockcheck+"/"+id+"?accesstoken="+"password";
        hs.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate rs = new RestTemplate();
        ResponseEntity<String> out = null;
        out = rs.exchange(url, HttpMethod.GET, new HttpEntity<>("", hs), String.class);
        if(out.getStatusCode().equals(HttpStatus.OK)){
            return Objects.equals(out.getBody(), "OK");
        }else{
        return false;
        }
    }
}
