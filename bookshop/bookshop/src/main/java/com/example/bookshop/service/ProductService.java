package com.example.bookshop.service;//package com.example.bookshop.service;
//
//import com.example.bookshop.entity.Product;
//
//import java.util.List;
//
//public interface ProductService {
//    public List<Product> findAll();
//}
import com.example.bookshop.dto.ProductDTO;
import com.example.bookshop.dto.ProductSearchCriteria;
import com.example.bookshop.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findProducts(String keyword);
    void updatStatus(Integer id, Integer status);
    public Product updateProductImage(Integer productId, String imageUrl);
    List<ProductDTO> getNewestProducts();
    List<ProductDTO> getAllByCategoriesID(Integer categoryId);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(int id);
    List<Product> searchProducts(ProductSearchCriteria productSearchCriteria);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(int id, ProductDTO productDTO);
    void deleteProduct(int id);
}
