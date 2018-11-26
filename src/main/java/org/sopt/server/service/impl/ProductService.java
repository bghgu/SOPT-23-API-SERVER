package org.sopt.server.service.impl;

import org.sopt.server.dto.Product;
import org.sopt.server.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ds on 2018-11-24.
 */

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(final ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> getAllProduct() {
        return productMapper.findAll();
    }

    public Product getProduct(final int productId) {
        return productMapper.findById(productId);
    }

    public void saveProduct(final Product product) {
        productMapper.save(product);
    }

    public Product updateProduct(final Product product) {
        productMapper.update(product);
        return productMapper.findById(product.getId());
    }

    public void deleteProduct(final int id) {
        productMapper.deleteById(id);
    }
}

