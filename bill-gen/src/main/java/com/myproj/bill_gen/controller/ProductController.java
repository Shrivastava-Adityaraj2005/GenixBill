package com.myproj.bill_gen.controller;

import com.myproj.bill_gen.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myproj.bill_gen.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author HP
 **/

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);

    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try{
            Product product1 = service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/product/cart/{id}")
    public void addToCart(@PathVariable int id){
        Product product = service.getProduct(id);
        if(product!=null){
            if(product.getCartQuantity()>0){
                product.setAdded("true");
            }
            else{
                product.setAdded("false");
            }
        }
        service.editQuantity(product);

    }

    @PutMapping("/product/{id}")
    public void editQuantity(@PathVariable int id){
         Product product = service.getProduct(id);
         if(product!=null){
             if(product.getQuantity()>=1){
                 product.setQuantity(product.getQuantity()-1);
                 product.setCartQuantity(product.getCartQuantity()+1);
             }
         }
         service.editQuantity(product);
    }
    @PutMapping("/product/reduce/{id}")
    public void reduceQuantity(@PathVariable int id){
         Product product = service.getProduct(id);
         if(product!=null){
             if(product.getCartQuantity()>=1){
                 product.setQuantity(product.getQuantity()+1);
                 product.setCartQuantity(product.getCartQuantity()-1);
             }
         }
         service.editQuantity(product);
    }

    @PutMapping("/productEdit/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) throws IOException {
        Product product1 = service.getProduct(id);
        if(product1!=null){
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            product1.setQuantity(product.getQuantity());

            product1.setImageName(imageFile.getOriginalFilename());
            product1.setImageType(imageFile.getContentType());
            product1.setImageData(imageFile.getBytes());
            service.updateProduct(product1);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
         Product product = service.getProduct(id);

         if(product!=null){

             service.deleteProduct(id);

                return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);

             }
         else{
             return new ResponseEntity<>("Failed to delete", HttpStatus.NOT_FOUND);
         }
    }


}
