package com.myproj.bill_gen.service;

import com.myproj.bill_gen.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myproj.bill_gen.repository.ProductRepo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author HP
 **/
@Service
public class ProductService {
    @Autowired
    ProductRepo repo;

    public List<Product> getAllProduct(){

        return repo.findAll();
    }

    public Product getProduct(int id) {

        return repo.findById(id).orElse(new Product());
    }


    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }
    public void editQuantity(Product product){
        repo.save(product);
    }

    public void deleteProduct(int id) {
         repo.deleteById(id);
    }

    public void updateProduct(Product product) throws IOException {

        repo.save(product);
    }
}
