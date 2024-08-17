package org.data.homework03.service;

import org.data.homework03.model.dto.request.ProductRequest;
import org.data.homework03.model.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductRequest productRequest);
    List<Product> getAllProducts(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
    Product getProductById(Long productId);
    Product updateProduct(Long productId, ProductRequest productRequest);
    void deleteProduct(Long productId);

}
