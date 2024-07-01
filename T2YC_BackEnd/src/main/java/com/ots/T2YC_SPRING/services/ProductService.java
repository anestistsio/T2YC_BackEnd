package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.ProductDto;
import com.ots.T2YC_SPRING.dto.ProductMiniDto;
import com.ots.T2YC_SPRING.entities.Product;
import com.ots.T2YC_SPRING.exceptions.ExistingException;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> allProducts(){
        return productRepository.findAll()
                .stream()
                .filter(Product::isActive)
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public ProductDto findProductById(int id){
        return new ProductDto(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id)));
    }

    @Transactional
    public Product createProduct(ProductMiniDto theProductMiniDto){
        Product newProduct = new Product(theProductMiniDto);
        if(productRepository.findByName(newProduct.getName()).isPresent()) {
            throw new ExistingException("This product name is used " + newProduct.getName());
        }else {
            newProduct.setActive(true);
            return productRepository.save(newProduct);
        }
    }

    @Transactional
    public void deactivateProductById(int id){
        Product deactivatedProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        deactivatedProduct.setActive(false);
        productRepository.save(deactivatedProduct);
    }

    @Transactional
    public void deactivateAllProducts(){
        List<Product> allActiveProducts = productRepository.findAll()
                .stream()
                .toList();
        if (!allActiveProducts.isEmpty()) {
            for (Product product : allActiveProducts) {
                product.setActive(false);
                productRepository.save(product);
            }
        }else {
            throw new NotFoundException("There is not any active product");
        }
    }

    @Transactional
    public void updateProduct(int id,ProductDto updatedProductDto){
        Optional<Product> foundProduct = productRepository.findById(id);
        if(foundProduct.isPresent()){
            Product updatedProduct = new Product(updatedProductDto);
            updatedProduct.setId(id);
            productRepository.save(updatedProduct);
        }else {
            throw new NotFoundException("Product not found with id: " + id);
        }
    }
}
