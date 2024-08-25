package data_dual.benchmark_sql.domain.service;

import data_dual.benchmark_sql.domain.model.Product;
import data_dual.benchmark_sql.infrastructure.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchProductsByCategory(String searchTerm) {
        return productRepository.findByCategoryOrNameOrDescriptionOrPrice(searchTerm);
    }
}
