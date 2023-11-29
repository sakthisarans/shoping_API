package com.example.useractivity.dbconnect;

import com.example.useractivity.merchantorder.merchantorder;
import com.example.useractivity.merchantorder.merchantorderlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface morderdb extends MongoRepository<merchantorderlist,String> {
    @Query("{'id':?0}")
    public merchantorderlist findByid(String id);
}
