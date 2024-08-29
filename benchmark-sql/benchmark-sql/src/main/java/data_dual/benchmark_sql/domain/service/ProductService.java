package data_dual.benchmark_sql.domain.service;

import data_dual.benchmark_sql.domain.model.Product;
import data_dual.benchmark_sql.infrastructure.ProductRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final Timer productRetrievalTimer;

    private final Timer allProductsRetrievalTimer;
    public ProductService(ProductRepository productRepository, MeterRegistry meterRegistry) {
        this.productRepository = productRepository;

        // Initialize Timer for measuring product retrieval time
        this.productRetrievalTimer = Timer.builder("one.product.retrieval.time")
                .description("Time taken to retrieve a product by name")
                .register(meterRegistry);

        this.allProductsRetrievalTimer = Timer.builder("all.products.retrieval.time")
                .description("Time taken to retrieve all products")
                .register(meterRegistry);
    }

    public List<Product> searchProductsByCategory(String searchTerm) {
        return productRepository.findByCategoryOrNameOrDescriptionOrPrice(searchTerm);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return allProductsRetrievalTimer.record(() -> productRepository.findAll());
    }
}
