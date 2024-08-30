package data_dual.benchmark_sql.domain.service;

import data_dual.benchmark_sql.application.SearchRequest;
import data_dual.benchmark_sql.domain.model.Product;
import data_dual.benchmark_sql.infrastructure.ProductRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final Timer productByCategoryRetrievalTimer;

    private final Timer allProductsRetrievalTimer;
    public ProductService(ProductRepository productRepository, MeterRegistry meterRegistry) {
        this.productRepository = productRepository;

        // Initialize Timer for measuring product retrieval time
        this.productByCategoryRetrievalTimer = Timer.builder("product.by.category.retrieval.time")
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

    public List<Product> search(SearchRequest searchRequest) {
        Specification<Product> spec = Specification.where(null);

        if (searchRequest.keyword() != null && !searchRequest.keyword().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchRequest.keyword().toLowerCase() + "%"));
        }

        if (searchRequest.category() != null && !searchRequest.category().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category"), searchRequest.category()));
        }

        if (searchRequest.brand() != null && !searchRequest.brand().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("brand"), searchRequest.brand()));
        }

        if (searchRequest.minPrice() != null && searchRequest.maxPrice() != null) {
            if (searchRequest.minPrice() >= 0 && searchRequest.maxPrice() >= 0) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.between(root.get("price"), searchRequest.minPrice(), searchRequest.maxPrice()));
            } else if (searchRequest.minPrice() >= 0) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchRequest.minPrice()));
            } else if (searchRequest.maxPrice() >= 0) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchRequest.maxPrice()));
            }
        }

        return productRepository.findAll(spec);
    }
}
