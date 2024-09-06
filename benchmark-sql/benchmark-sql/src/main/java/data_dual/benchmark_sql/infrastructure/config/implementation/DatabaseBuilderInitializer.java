package data_dual.benchmark_sql.infrastructure.config.implementation;

import com.github.javafaker.Faker;
import data_dual.benchmark_sql.domain.model.Product;
import data_dual.benchmark_sql.infrastructure.ProductRepository;
import data_dual.benchmark_sql.infrastructure.config.OnDatabaseInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Qualifier("builder")
public class DatabaseBuilderInitializer implements OnDatabaseInit {

    @Value("${database.max-size}")
    private int maxSize;

    private final ProductRepository productRepository;

    public DatabaseBuilderInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void run() {
        productRepository.deleteAll();

        Faker faker = new Faker();
        for (int i = 0; i < maxSize; i++) {
            String name = faker.commerce().productName();
            String description = faker.lorem().sentence();
            Double price = Double.parseDouble(faker.commerce().price().replace(",","."));
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

        log.info("Database initialized with random products");
    }
}