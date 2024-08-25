package data.dual.benchmark.nosql.infrastructure;

import data.dual.benchmark.nosql.domain.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(String category);
}
