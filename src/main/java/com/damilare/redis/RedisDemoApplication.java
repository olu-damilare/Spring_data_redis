package com.damilare.redis;

import com.damilare.redis.entities.Product;
import com.damilare.redis.repositories.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class RedisDemoApplication {

    @Autowired
    private ProductDao dao;

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    @PostMapping
    public Product save(@RequestBody Product product){
        return dao.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return dao.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product", unless = "#result.price > 1000")
    public Product findProduct(@PathVariable int id){
        return dao.findProductById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id", value = "Product")
    public String deleteProduct(@PathVariable int id){
        return dao.deleteProduct(id);
    }

}
