package data.dual.benchmark.nosql.domain.config;

import data.dual.benchmark.nosql.domain.model.Product;
import data.dual.benchmark.nosql.infrastructure.ProductRepository;
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
            Product product = new Product(
                    String.valueOf(i),
                    "Product " + i,
                    "Description for Product " + i,
                    20.0 + i,
                    100 - i,
                    "Category " + (i % 3)
            );
            products.add(product);
        }
        try {
            productRepository.insert(products);
        }catch (Exception e){
            System.out.println("data was inserted");
        }
    }
}
