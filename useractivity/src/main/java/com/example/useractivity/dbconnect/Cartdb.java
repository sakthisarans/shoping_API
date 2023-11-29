package com.example.useractivity.dbconnect;

import com.example.useractivity.cart.cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface Cartdb extends MongoRepository<cart,String> {
    @Query("{'id':?0}")
    public cart findByid(String id);
}
