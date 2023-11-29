package com.example.useractivity.merchantorder;

import com.example.useractivity.shoppinghistory.product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data@Getter@Setter
public class merchantorder {
    private String productid;
    private String sellerid;
    private int count;
    private String orderid;
    private boolean packed;
    private boolean shipped;
    private String date;
    @Builder.Default
    private boolean cancelled=false;
    public merchantorder(product x,String orderid){
        this.orderid=orderid;
        this.productid=x.getProductid();
        this.sellerid=x.getSellerid();
        this.count=x.getCount();
        this.packed=false;
        this.shipped=false;
        this.date= String.valueOf(new Date().getTime());
    }
    public merchantorder(){}
}
