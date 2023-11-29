package com.example.shopping.service;

import com.example.shopping.productdb.tokendb;
import com.example.shopping.token.accesstokens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class accesstokenvalidator {
    Logger logger = LoggerFactory.getLogger(accesstokenvalidator.class);
    @Autowired(required = false)
    private tokendb token;
    public  Object[] tokenvalidator(String accesstoken){
        logger.info("----------------------");
        logger.info(accesstoken);
        accesstokens obj=token.findByaccesstoken(accesstoken);
        System.out.println(obj);
        Object[] out=new Object[3];
        if(obj!=null) {
            LocalDateTime ldt = LocalDateTime.parse(obj.getToken().getExpiry_date());
            LocalDateTime ldtexp = LocalDateTime.now();
            out[0]=ldtexp.isBefore(ldt);
            out[1]=obj.getId();
            out[2]=obj.getRoll();
            System.out.println(out);
            return out;
        }else{
            out[0]=false;
            out[1]=null;
            out[2]=null;
            return out;
        }
    }
}
