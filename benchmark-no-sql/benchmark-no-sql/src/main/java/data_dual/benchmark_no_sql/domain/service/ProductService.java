package data_dual.benchmark_no_sql.domain.service;

import data_dual.benchmark_no_sql.domain.model.Product;
import data_dual.benchmark_no_sql.infrastructure.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> searchProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
