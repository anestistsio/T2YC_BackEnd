package com.ots.T2YC_SPRING.controllers;

import com.ots.T2YC_SPRING.dto.ProductDto;
import com.ots.T2YC_SPRING.dto.ProductMiniDto;
import com.ots.T2YC_SPRING.entities.Product;
import com.ots.T2YC_SPRING.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<ProductDto> getAllProduct(){
        return productService.allProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable int id){
        return productService.findProductById(id);
    }

    @PostMapping
    public ResponseEntity<String> addNewSupportAgent(@RequestBody ProductMiniDto newProductMiniDto){
        if (newProductMiniDto == null){
            return ResponseEntity.badRequest().body("Invalid request body");
        }
        Product createdProduct = productService.createProduct(newProductMiniDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/deactivate/{id}")
    public void deactivateProduct(@PathVariable int id){
        productService.deactivateProductById(id);
    }

    @PutMapping("/deactivate/all")
    public void deactivateAllProduct(){
        productService.deactivateAllProducts();
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id,@RequestBody ProductDto updatedProductDto){
        productService.updateProduct(id, updatedProductDto);
    }

}
