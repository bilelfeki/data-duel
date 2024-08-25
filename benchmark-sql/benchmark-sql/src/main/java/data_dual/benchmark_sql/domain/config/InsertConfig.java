package data_dual.benchmark_sql.domain.config;

import data_dual.benchmark_sql.domain.model.Product;
import data_dual.benchmark_sql.infrastructure.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class InsertConfig implements CommandLineRunner {
    private final ProductRepository productRepository;
    @Override
    public void run(String... args) throws Exception {
        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            products.add(new Product((long) i, "Product " + i, "Description for Product " + i, 10.0 * i, 100 - i, "Category " + (i % 3)));
        }
        try {
            productRepository.saveAll(products);
        } catch (Exception e) {
            System.out.println("data was inserted");
        }

    }
}
