package com.example.useractivity.dbconnect;

import com.example.useractivity.shoppinghistory.shopping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface Orderdata extends MongoRepository<shopping,String> {

    @Query("{'orderid':?0}")
    public shopping findByOrderid(String orderid);
    @Query("{'userid':?0}")
    public List<shopping> findByUserid(String userid);
}

