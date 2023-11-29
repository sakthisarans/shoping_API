package com.example.useractivity.service;

import com.example.useractivity.dbconnect.morderdb;
import com.example.useractivity.merchantorder.merchantorder;
import com.example.useractivity.merchantorder.merchantorderlist;
import com.example.useractivity.shoppinghistory.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class merchentordercreatior {
    @Autowired
    morderdb modb;
    public void createorder(List<product> order,String orderid){
        System.out.println("-----------------------------------------------------------------");
        order.forEach(x->{
            System.out.println("/////////////////////////////////////////////////////////////");
            merchantorder mo=new merchantorder(x,orderid);
            System.out.println("-----------------------------------------------------------------");
            System.out.println(modb.findByid(x.getSellerid()));
            merchantorderlist temp=modb.findByid(x.getSellerid());
            System.out.println("****************************************************************");
            if(temp==null){
                merchantorderlist temp1=new merchantorderlist();
                temp1.setId(x.getSellerid());
                List<merchantorder> temp3=new ArrayList<>();
                temp3.add(mo);
                temp1.setOrder(temp3);
                modb.save(temp1);
            }else{
                List<merchantorder> temp1=new ArrayList<>();
                temp1.addAll(temp.getOrder());
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                temp1.add(mo);
                temp.setOrder(temp1);
                modb.save(temp);
            }
        });
    }
}
