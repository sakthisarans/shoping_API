package com.example.abhi.dbconnect;
import com.example.abhi.accesstoken.accesstokens;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository@Configuration
public interface accesstokendb extends MongoRepository<accesstokens,String> {
    public accesstokens findByid(String id);
    @Query("{ 'token.accesstoken' : ?0 }")
//    @Query("{ 'id' : ?0 }")
    accesstokens findByaccesstoken(String accesstoken);
}
