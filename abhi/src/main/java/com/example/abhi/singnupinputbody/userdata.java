package com.example.abhi.singnupinputbody;

// import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@Setter
public class userdata {
//    @JsonIgnore
    @Getter@Id
    private String id;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String requestType;
    @Builder.Default
    private boolean twofactor=false;
    private String phoneNumber;
    private String email;
    private List<String> userType;
    private String siteId;
    private String customerId;
    private String password;
    private String accessCode;
    private String source;
    private String countryCode;
    private boolean dealsAndDiscountsNewsLetters;
    private boolean acceptAgreement;
    private String multiChannelSource;
    private boolean dontHaveAccessCode;
    private String corporateEmail;
     @Builder.Default
    private String sourceFrom="";

     public userdata(){}
}
