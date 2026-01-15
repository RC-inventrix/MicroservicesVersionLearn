package com.programmingtechi.productservice.controller;

import com.programmingtechi.productservice.dto.ProductRequest;
import com.programmingtechi.productservice.dto.ProductResponse;
import com.programmingtechi.productservice.model.Product;
import com.programmingtechi.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        // Logic to save the product would go here

        productService.createProduct(productRequest);


    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {

        return productService.getAllProducts();
    }







}
