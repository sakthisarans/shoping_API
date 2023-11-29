package com.example.abhi.signinbody;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter@Setter@Data
public class deviceFingerPrint {
    private String deviceFingerPrintId;
    private String ipAddress;
    private String fingerPrintId;
    @JsonProperty("browserInfo")
    browserInfo browserInfo;
    deviceFingerPrint(){}
}
