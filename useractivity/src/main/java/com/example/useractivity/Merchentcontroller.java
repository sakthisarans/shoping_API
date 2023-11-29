package com.example.useractivity;

import com.example.useractivity.dbconnect.morderdb;
import com.example.useractivity.service.accesstokenvalidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/merchent")
public class Merchentcontroller {
    @Autowired
    accesstokenvalidator valid;
    @Autowired
    morderdb mo;
    @GetMapping("/myorder")
    public ResponseEntity<?> myorder(@RequestParam(required = true)String accesstoken){
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access tokendb is mandidatory", HttpStatus.OK);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]) {
            if(((List<String>)valid.tokenvalidator(accesstoken)[2]).contains("merchant")) {
                return new ResponseEntity<>(mo.findByid((String) valid.tokenvalidator(accesstoken)[1]),HttpStatus.OK);
            }else{
                return new ResponseEntity<>("access denied", HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>("invalid accesstoken", HttpStatus.OK);
        }
    }

}
