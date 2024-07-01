package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.ProductDto;
import com.ots.T2YC_SPRING.dto.ProductMiniDto;
import com.ots.T2YC_SPRING.dto.SupportAgentMiniDto;
import com.ots.T2YC_SPRING.entities.Product;
import com.ots.T2YC_SPRING.entities.SupportAgent;
import com.ots.T2YC_SPRING.exceptions.ExistingException;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceIT {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;


    @Test
    void allProducts() {
        List<ProductDto> result = productService.allProducts();
        int size = productRepository.findAll()
                .stream()
                .filter(Product::isActive)
                .toList()
                .size();
        assertEquals(size ,result.size());
    }

    @Test
    void findProductById() {
        Product product = Product.builder()
                .name("Product_1")
                .agentsThatSupportThisProduct(List.of())
                .build();

        productRepository.save(product);
        ProductDto result = productService.findProductById(product.getId());

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
    }

    @Test
    void findProductById_NotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.findProductById(99));
        assertEquals("Product not found with id: 99", exception.getMessage());
    }

    @Test
    void createProduct() {
        ProductMiniDto productMiniDto = ProductMiniDto.builder()
                .name("Product_88")
                .build();

        Product result = productService.createProduct(productMiniDto);
        assertNotNull(result);
    }

    @Test
    void tryToCreateExistingProduct(){
        ProductMiniDto productMiniDto = ProductMiniDto.builder()
                .name("Product A")
                .build();
        ExistingException exception = assertThrows(ExistingException.class, () -> productService.createProduct(productMiniDto));
    }

    @Test
    void deactivateProductById() {
        productService.deactivateProductById(2);
        assertFalse(productRepository.findById(2).orElseThrow(() -> new NotFoundException("")).isActive());
    }

    @Test
    void deactivateProductById_NotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.deactivateProductById(99));
        assertEquals("Product not found with id: 99", exception.getMessage());
    }

    @Test
    void deactivateAllProducts() {
        productService.deactivateAllProducts();
        List<Product> allProducts = productRepository.findAll().stream()
                .toList();
        for (Product product : allProducts ) {
            assertFalse(product.isActive());
        }
    }

    @Test
    void updateProduct() {
        ProductDto updatedProductDto = ProductDto.builder()
                .name("Product A")
                .agentsThatSupportThisProduct(List.of())
                .build();

        productService.updateProduct(1, updatedProductDto);
        Product product = productRepository.findById(1).orElseThrow();
        assertEquals(product.getName(),updatedProductDto.getName());
    }

    @Test
    void updateProduct_NotFound() {
        ProductDto updatedProductDto = ProductDto.builder()
                .name("Product_99")
                .agentsThatSupportThisProduct(List.of(new SupportAgentMiniDto()))
                .build();

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.updateProduct(99, updatedProductDto));
        assertEquals("Product not found with id: 99", exception.getMessage());
    }


}