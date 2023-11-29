package com.example.abhi.singnupinputbody;


import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;
@Data
@Getter@Setter
@AllArgsConstructor
public class userdatatest {
    @Getter
    @Id
    private String id;
    private String firstName;
    private String lastName;

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
    public userdatatest(){}
}
