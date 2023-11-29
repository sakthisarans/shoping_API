package com.example.useractivity.dbconnect;

import com.example.useractivity.token.accesstokens;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface tokendb extends MongoRepository<accesstokens,String> {
    @Query("{ 'token.accesstoken' : ?0 }")
    accesstokens findbytoken(String token);
}
