package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.ProductDto;
import com.ots.T2YC_SPRING.dto.ProductMiniDto;
import com.ots.T2YC_SPRING.dto.SupportAgentMiniDto;
import com.ots.T2YC_SPRING.entities.Product;
import com.ots.T2YC_SPRING.entities.SupportAgent;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void allProducts() {
        Product product1 = Product.builder()
                .id(1)
                .name("Product_1")
                .agentsThatSupportThisProduct(List.of(new SupportAgent()))
                .isActive(true)
                .build();
        Product product2 = Product.builder()
                .id(2)
                .name("Product_2")
                .agentsThatSupportThisProduct(List.of(new SupportAgent()))
                .isActive(true)
                .build();
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<ProductDto> result = productService.allProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findProductById() {
        Product product = Product.builder()
                .id(1)
                .name("Product_1")
                .agentsThatSupportThisProduct(List.of(new SupportAgent()))
                .isActive(true)
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ProductDto result = productService.findProductById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void findProductById_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.findProductById(1));

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void createProduct() {
        ProductMiniDto productMiniDto = ProductMiniDto.builder()
                .name("Product_1")
                .build();
        Product product = new Product(productMiniDto);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(productMiniDto);

        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProductById() {
        Product product = Product.builder()
                .id(1)
                .name("Product_1")
                .agentsThatSupportThisProduct(List.of(new SupportAgent()))
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        productService.deactivateProductById(1);

        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProductById_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.deactivateProductById(1));

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(0)).deleteById(1);
    }

    @Test
    void updateProduct() {
        ProductDto updatedProductDto = ProductDto.builder()
                .id(1)
                .name("Product_1")
                .agentsThatSupportThisProduct(List.of(new SupportAgentMiniDto()))
                .build();

        Product product = new Product(updatedProductDto);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.updateProduct(1, updatedProductDto);

        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_NotFound() {
        ProductDto updatedProductDto = ProductDto.builder()
                .id(1)
                .name("Product_1")
                .agentsThatSupportThisProduct(List.of(new SupportAgentMiniDto()))
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.updateProduct(1, updatedProductDto));

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(0)).save(any(Product.class));
    }
}
