package com.hamsoft.restapi.service;

import com.hamsoft.restapi.domain.Product;
import com.hamsoft.restapi.domain.Product;

import java.util.Optional;

public interface ProductService {

    Iterable<Product> listAllProduct();

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProduct(Long id);
}
