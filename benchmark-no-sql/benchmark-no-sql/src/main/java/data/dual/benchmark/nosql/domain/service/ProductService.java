package data.dual.benchmark.nosql.domain.service;

import data.dual.benchmark.nosql.domain.model.Product;
import data.dual.benchmark.nosql.infrastructure.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
