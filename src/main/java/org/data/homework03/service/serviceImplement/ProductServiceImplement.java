package org.data.homework03.service.serviceImplement;

import lombok.AllArgsConstructor;
import org.data.homework03.model.dto.request.ProductRequest;
import org.data.homework03.model.entity.Product;
import org.data.homework03.repository.ProductRepository;
import org.data.homework03.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImplement implements ProductService {

    private ProductRepository productRepository;

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setDescription(productRequest.getDescription());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        if (pageNo == null || pageNo <= 0 || pageSize == null || pageSize <= 0 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page number");
        }

        if (sortBy == null || sortDirection == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort field and direction must be specified!");
        }

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
        Page<Product> getAllPage = productRepository.findAll(pageable);
        return getAllPage.getContent();
    }

    @Override
    public Product getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return product;
    }

    @Override
    public Product updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setProductName(productRequest.getProductName());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setDescription(productRequest.getDescription());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
                productRepository.delete(product);
    }
}
