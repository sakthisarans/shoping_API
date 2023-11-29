package com.example.abhi.dbconnect;

import com.example.abhi.signinresponse.accesslog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface signin extends MongoRepository<accesslog, String> {
    @Query("{'id':?0}")
    public accesslog findByid(String id);

}
