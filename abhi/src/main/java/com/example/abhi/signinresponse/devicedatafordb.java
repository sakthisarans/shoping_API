package com.example.abhi.signinresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter@Setter@Data
public class devicedatafordb {



    private String deviceFingerPrintId;
    private String ipAddress;
    private String fingerPrintId;
    @JsonProperty("browserInfo")
    com.example.abhi.signinresponse.browserInfo browserInfo;
    public devicedatafordb(){}
}
