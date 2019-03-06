package com.hamsoft.restapi.service;

import com.hamsoft.restapi.domain.Product;
import com.hamsoft.restapi.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> listAllProduct();

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProduct(Long id);
}
