package com.example.useractivity.service;

import com.example.useractivity.dbconnect.tokendb;
import com.example.useractivity.token.accesstokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class accesstokenvalidator {
    @Autowired
    tokendb token;
    public  Object[] tokenvalidator(String accesstoken){
        accesstokens obj=token.findbytoken(accesstoken);
        Object[] out=new Object[3];
        if(obj!=null) {
            LocalDateTime ldt = LocalDateTime.parse(obj.getToken().getExpiry_date());
            LocalDateTime ldtexp = LocalDateTime.now();
            out[0]=ldtexp.isBefore(ldt);
            out[1]=obj.getId();
            out[2]=obj.getRoll();
            return out;
        }else{
            out[0]=false;
            out[1]=null;
            out[2]=null;
            return out;
        }
    }
}
