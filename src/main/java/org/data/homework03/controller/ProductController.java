package org.data.homework03.controller;

import lombok.AllArgsConstructor;
import org.data.homework03.model.dto.request.ProductRequest;
import org.data.homework03.model.dto.response.ApiResponse;
import org.data.homework03.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("create-product")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Product created successfully")
                .status(HttpStatus.CREATED)
                .payload(productService.createProduct(productRequest))
                .code(201)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<?>> getAllProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(defaultValue = "productId") String sortBy,
                                                  @RequestParam(defaultValue = "ASC") String sortDirection) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Get all products successfully")
                .status(HttpStatus.OK)
                .payload(productService.getAllProducts(pageNo, pageSize, sortBy, sortDirection))
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(Collections.singletonList(apiResponse));
    }

    @GetMapping("get-byId/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Get product with ID: " + productId + " successfully")
                .status(HttpStatus.OK)
                .payload(productService.getProductById(productId))
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("update-product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody ProductRequest productRequest) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Update product with ID: " + productId + " successfully")
                .status(HttpStatus.OK)
                .payload(productService.updateProduct(productId, productRequest))
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Product deleted with ID: " + productId + " successfully")
                .status(HttpStatus.OK)
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
