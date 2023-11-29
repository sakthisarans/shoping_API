package com.example.shopping.productdb;

import com.example.shopping.products.products;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface productdb extends MongoRepository<products,String> {
    @Bean
    @Query("{'productid':?0}")
    public products findByproductid(String productid);
    @Query("{productid: { $in: ?0 } })")
    public List<products> findByproductid(List<String> productid);

}
