package data.dual.benchmark.nosql.domain.service;

import data.dual.benchmark.nosql.domain.model.Product;
import data.dual.benchmark.nosql.infrastructure.ProductRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository,  MeterRegistry meterRegistry) {
        this.productRepository = productRepository;
    }

    public List<Product> searchProducts(String searchTerm) {
        return productRepository.findByCategoryOrNameOrDescriptionOrPrice(searchTerm);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
