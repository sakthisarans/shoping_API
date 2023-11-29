package com.example.abhi.signinbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter@Setter
@Data
public class browserInfo {
    private String browserName;
    private String fullVersion;
    private String majorVersion;
    private String userAgent;
    private String url;
    private String ipAddress;
    private String os;
    private String deviceName;
    private String deviceType;
    browserInfo(){}

}
