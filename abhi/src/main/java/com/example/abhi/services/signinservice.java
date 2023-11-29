package com.example.abhi.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class signinservice {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    
    public String Accesstokengen(){
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);

        return base64Encoder.encodeToString(randomBytes);
    }
    public boolean timestamp(String time){
        LocalDateTime ldt = LocalDateTime.parse(time);
        LocalDateTime ldtexp = LocalDateTime.now();

        if(ldtexp.isBefore(ldt)){
            System.out.println("--------------------------"+"true");
            System.out.println(ldt);
            System.out.println(ldtexp);
            return false;
        }else {
            System.out.println("--------------------------"+"true");
            return true;
        }
    }
    public String timestampgenerator(){
        LocalDateTime ldt=LocalDateTime.now();
        return ldt.toString();
    }
    public String timestampgenerator(int num){
        LocalDateTime ldt=(LocalDateTime.now()).plusDays(num);
        return ldt.toString();
    }
}
