package data.dual.benchmark.nosql.infrastructure;

import data.dual.benchmark.nosql.application.SearchRequest;
import data.dual.benchmark.nosql.domain.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ '$or': [ " +
            "{ 'category': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'name': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'description': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'price': { '$regex': ?0, '$options': 'i' } } " +
            "] }")
    List<Product> findByCategoryOrNameOrDescriptionOrPrice(String searchTerm);
}
