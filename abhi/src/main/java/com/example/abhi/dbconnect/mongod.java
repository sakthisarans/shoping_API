package com.example.abhi.dbconnect;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.abhi.singnupinputbody.userdata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository@Component
public interface mongod extends MongoRepository<userdata,String> {
    @Query("{'email':?0}")
    public userdata finfindByemail(String email);
}
