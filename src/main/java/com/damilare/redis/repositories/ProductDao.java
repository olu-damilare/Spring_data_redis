package com.damilare.redis.repositories;

import com.damilare.redis.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private RedisTemplate template;
    public static final String HASH_KEY = "Product";

    public Product save(Product product){
        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Product> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }


    public Product findProductById(int id){
        System.out.println("call to db");
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProduct(int id){
        template.opsForHash().delete(HASH_KEY, id);
        return "Product deleted";
    }
}
