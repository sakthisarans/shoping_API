package com.example.shopping.productdb;

import com.example.shopping.token.accesstokens;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface tokendb extends MongoRepository<accesstokens,String> {
    @Query("{ 'token.accesstoken' : ?0 }")
//    @Query("{ 'id' : ?0 }")
    accesstokens findByaccesstoken(String accesstoken);
}
