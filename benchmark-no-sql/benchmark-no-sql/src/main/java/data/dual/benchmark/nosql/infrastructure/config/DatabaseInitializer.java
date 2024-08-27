package data.dual.benchmark.nosql.infrastructure.config;

import com.github.javafaker.Faker;
import data.dual.benchmark.nosql.domain.model.Product;
import data.dual.benchmark.nosql.infrastructure.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Value("${database.max-size}")
    private int maxSize;

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        productRepository.deleteAll();
        return args -> {
            Faker faker = new Faker();

            // Create products
            for (int i = 0; i < maxSize; i++) {
                String name = faker.commerce().productName();
                String description = faker.lorem().sentence();
                double price = Double.parseDouble(faker.commerce().price());
                String category = faker.commerce().department();
                int stock = faker.number().numberBetween(0, 100);

                Product product = Product.builder()
                        .name(name)
                        .category(category)
                        .price(price)
                        .description(description)
                        .stock(stock)
                        .build();
                productRepository.save(product);
            }

            System.out.println("Database initialized with random products");
        };
    }
}
