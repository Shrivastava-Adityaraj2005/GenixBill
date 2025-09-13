package com.myproj.bill_gen.repository;

import com.myproj.bill_gen.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HP
 **/
@Repository
public interface ProductRepo extends JpaRepository<Product,Integer > {
}
