package data.dual.benchmark.nosql.domain.service;

import data.dual.benchmark.nosql.application.SearchRequest;
import data.dual.benchmark.nosql.domain.model.Product;
import data.dual.benchmark.nosql.infrastructure.ProductRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    public ProductService(ProductRepository productRepository, MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Product> searchProducts(String searchTerm) {
        return productRepository.findByCategoryOrNameOrDescriptionOrPrice(searchTerm);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> search(SearchRequest searchRequest) {
        Query query = new Query();

        if (searchRequest.keyword() != null && !searchRequest.keyword().isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(searchRequest.keyword(), "i"));
        }

        if (searchRequest.category() != null && !searchRequest.category().isEmpty()) {
            query.addCriteria(Criteria.where("category").is(searchRequest.category()));
        }

        if (searchRequest.brand() != null && !searchRequest.brand().isEmpty()) {
            query.addCriteria(Criteria.where("brand").is(searchRequest.brand()));
        }

        // Combine minPrice and maxPrice criteria in a single Criteria object
        Criteria priceCriteria = new Criteria();
        if (searchRequest.minPrice() != null && searchRequest.maxPrice() != null){
            if (searchRequest.minPrice() >= 0 && searchRequest.maxPrice() >= 0) {
                priceCriteria = Criteria.where("price").gte(searchRequest.minPrice()).lte(searchRequest.maxPrice());
            } else if (searchRequest.minPrice() >= 0) {
                priceCriteria = Criteria.where("price").gte(searchRequest.minPrice());
            } else if (searchRequest.maxPrice() >= 0) {
                priceCriteria = Criteria.where("price").lte(searchRequest.maxPrice());
            }
            query.addCriteria(priceCriteria);
        }

        return mongoTemplate.find(query, Product.class);
    }
}
