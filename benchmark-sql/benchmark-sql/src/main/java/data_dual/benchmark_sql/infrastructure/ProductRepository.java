package data_dual.benchmark_sql.infrastructure;

import data_dual.benchmark_sql.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "CAST(p.price AS string) LIKE CONCAT('%', :searchTerm, '%')")
    List<Product> findByCategoryOrNameOrDescriptionOrPrice(String searchTerm);

}
