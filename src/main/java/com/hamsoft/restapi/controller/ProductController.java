package com.hamsoft.restapi.controller;

import com.hamsoft.restapi.domain.Product;
import com.hamsoft.restapi.exception.ResourceNotFoundException;
import com.hamsoft.restapi.service.ProductService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Products")
@RestController
@RequestMapping(APIName.PRODUCTS)
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.listAllProduct();
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product){
        return  productService.saveProduct(product);
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable(value = "id") Long productId ){

        return productService.getProductById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","id",productId));
    }

    @PutMapping("{id}")
    public  Product updateProduct(@PathVariable(value = "id") Long productId , @Valid @RequestBody Product productRequest){
        return  productService.getProductById(productId).map(
                product -> {
                    product.setName(productRequest.getName());
                    return  productService.saveProduct(product);
                }

        ).orElseThrow(()->new ResourceNotFoundException("Product","id",productId));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") Long productId) {
        return productService.getProductById(productId).map(product -> {
            productService.deleteProduct(product.getId());
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Product","id",productId));
    }
}
