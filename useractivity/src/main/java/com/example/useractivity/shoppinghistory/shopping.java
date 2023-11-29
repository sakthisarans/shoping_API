package com.example.useractivity.shoppinghistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Data@AllArgsConstructor
@Getter@Setter
public class shopping {
    @Id@JsonIgnore
    private String id;
    private String userid;
    private List<product> product;
    private String orderid;
    private String date;
    private List<Object> update;
    @Builder.Default
    private boolean cancel=false;
    @JsonIgnore
    private boolean paymentinitiative;
    @JsonIgnore
    @Builder.Default
    private boolean paymentcomplete=false;
    @Builder.Default
    private boolean orderconfirm=false;
    @Builder.Default
    private int addressid=0;
    public shopping(){
        String.valueOf(new Date().getTime());
    }

}
