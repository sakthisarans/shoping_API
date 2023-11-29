package com.example.abhi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.abhi.accesstoken.accesstoken;
import com.example.abhi.accesstoken.accesstokens;
import com.example.abhi.dbconnect.accesstokendb;
import com.example.abhi.dbconnect.signin;
import com.example.abhi.signinresponse.devicedatafordb;
import com.example.abhi.signinresponse.accesslog;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.abhi.dbconnect.mongod;
import com.example.abhi.services.signinservice;
import com.example.abhi.signinbody.signininputdata;
import com.example.abhi.singnupinputbody.userdata;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
//@ComponentScan
// @CrossOrigin(origins = "http://google.com")
@RequestMapping("/endpoint")
public class endpointcontroller {
    @Autowired
    accesstokendb ac;
    @Autowired
    signinservice sc;
    @Autowired
    private signin sin;
    @Autowired
    private mongod mon;
    String decision;
    @PostMapping("/")
    public ResponseEntity<?> usercontroller(@RequestBody Object obj){
        System.out.println("-----------------------------------------------");
        System.out.println(obj);
        ObjectMapper map=new ObjectMapper();
        JsonNode nod=map.convertValue(obj, JsonNode.class);
        decision=(nod.get("requestType").toString().replace("\"", ""));
        if(decision.equals("SignUp")){
            userdata in=map.convertValue(obj, userdata.class);
            if(mon.finfindByemail(in.getEmail())==null){
                return new ResponseEntity<>(mon.save(in),HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>("user already exists",HttpStatus.ALREADY_REPORTED);
            }
        }
        else if(decision.equals("SignIn"))
        {
            signininputdata in =map.convertValue(obj, signininputdata.class);
            userdata uid=mon.finfindByemail(in.getEmail());
            if(uid==null){
                return new ResponseEntity<>("no user found",HttpStatus.NOT_FOUND);
            }
            else if(uid.getEmail().equals((in.getEmail().replace("\"","")))&&
            uid.getPassword().equals((in.getPassword().replace("\"","")))){
                devicedatafordb dd=map.convertValue(in.getDeviceFingerPrint(),devicedatafordb.class);
                accesslog temp=sin.findByid(uid.getId());
                System.out.println("??????????????????????????????????????????");
                System.out.println(dd);
                System.out.println(temp);
                if(temp!=null){
                    List<devicedatafordb> devicedata=temp.getDevicedata();
                    System.out.println((devicedata.get(devicedata.size()-1)).getIpAddress());
                    System.out.println(dd.getIpAddress());
                    if(!(((devicedata.get(devicedata.size()-1)).getIpAddress()).equals(dd.getIpAddress()))){
                        devicedata.add(dd);
                        temp.setDevicedata(devicedata);
                        sin.save(temp);
                    }
                }else{
                    List<devicedatafordb> devicedata=new ArrayList<devicedatafordb>();
                    devicedata.add(dd);
                    accesslog td=new accesslog();
                    td.setId(uid.getId());
                    td.setDevicedata(devicedata);
                    sin.save(td);
                }
                accesstokens list=ac.findByid(uid.getId());
                if(list!=null){
                    System.out.println("------------------------------------");
                    System.out.println(list);
                    accesstoken tempac=list.getToken();
                    if(tempac==null){
                        accesstokens newac=new accesstokens();
                        newac.setId(uid.getId());
                        accesstoken template=new accesstoken();
                        template.setAccesstoken(sc.Accesstokengen());
                        template.setUsername(uid.getFirstName());
                        template.setDate_of_creation(sc.timestampgenerator());
                        template.setExpiry_date( sc.timestampgenerator(3));
                        List<accesstoken> tempact = new ArrayList<>();
                        if(list.getAccesstoken()!=null){
                            tempact.addAll(list.getAccesstoken());
                            if(tempac!=null)
                                tempact.add(tempac);
                        }
                        else{
                            if(tempac!=null)
                                tempact.add(tempac);
                        }
                        list.setToken(template);
                        list.setAccesstoken(tempact);
                        list.setRoll(uid.getUserType());
                        ac.save(list);
                        return new ResponseEntity<>(list.getToken(),HttpStatus.OK);
                    }
                    else if(sc.timestamp(tempac.getExpiry_date())){
                        accesstokens newac=new accesstokens();
                        newac.setId(uid.getId());
                        accesstoken template=new accesstoken();
                        template.setAccesstoken(sc.Accesstokengen());
                        template.setUsername(uid.getFirstName());
                        template.setDate_of_creation(sc.timestampgenerator());
                        template.setExpiry_date( sc.timestampgenerator(3));
                        List<accesstoken> tempact = new ArrayList<>();
                        if(list.getAccesstoken()!=null){
                            tempact.addAll(list.getAccesstoken());
                            tempact.add(tempac);
                        }
                        else{
                            tempact.add(tempac);}
                        list.setToken(template);
                        list.setAccesstoken(tempact);
                        list.setRoll(uid.getUserType());
                        ac.save(list);
                        return new ResponseEntity<>(list.getToken(),HttpStatus.OK);
                    }else {
                        return new ResponseEntity<>(list.getToken(), HttpStatus.OK);
                    }
                }else{
                    accesstokens newac=new accesstokens();
                    newac.setId(uid.getId());
                    accesstoken tempac=new accesstoken();
                    tempac.setAccesstoken(sc.Accesstokengen());
                    tempac.setUsername(uid.getFirstName());
                    tempac.setDate_of_creation(sc.timestampgenerator());
                    tempac.setExpiry_date(sc.timestampgenerator(3));
                    newac.setToken(tempac);
                    newac.setRoll(uid.getUserType());
                    ac.save(newac);
                    return new ResponseEntity<>(newac.getToken(),HttpStatus.OK);
                }
            }
            else {
                return new ResponseEntity<>("no user found",HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>("null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/signout")
    public HttpStatus signout(@RequestParam(required = true) String accesstoken){
        System.out.println(accesstoken);
        char[] let={'z','c'};

        if(accesstoken.equals("")||accesstoken.isEmpty()||accesstoken.isBlank()){
            return HttpStatus.BAD_REQUEST;
        }else {
            System.out.println("----------------------------------------");
            accesstokens list = ac.findByaccesstoken(accesstoken);
            System.out.println("----------------------------------------");
            System.out.println(list);
            if(list!=null){
                accesstoken tempac=list.getToken();
                accesstokens newac=new accesstokens();
                newac.setId(list.getId());
                accesstoken template=null;
                List<accesstoken> tempact = new ArrayList<>();
                if(list.getAccesstoken()!=null){
                    tempact.addAll(list.getAccesstoken());
                    tempact.add(tempac);
                }
                else{
                    tempact.add(tempac);}
                list.setToken(template);
                list.setAccesstoken(tempact);
                ac.save(list);
                return HttpStatus.OK;
            }else{
                return HttpStatus.NOT_FOUND;
            }
        }

    }
}
