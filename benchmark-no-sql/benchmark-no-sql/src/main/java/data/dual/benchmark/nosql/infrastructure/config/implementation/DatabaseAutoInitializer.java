package data.dual.benchmark.nosql.infrastructure.config.implementation;


import com.github.javafaker.Faker;
import data.dual.benchmark.nosql.domain.model.Product;
import data.dual.benchmark.nosql.infrastructure.ProductRepository;
import data.dual.benchmark.nosql.infrastructure.config.OnInit;
import data.dual.benchmark.nosql.util.ClassHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("auto")
public class DatabaseAutoInitializer implements OnInit {

    @Value("${database.max-size}")
    private int maxSize;

    private final ProductRepository productRepository;
    private final ClassHandler<?> classHandler ;

    public void run() {
        productRepository.deleteAll();

        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= maxSize; i++) {
            Product product = new Product();
            for (Field field : Product.class.getDeclaredFields()) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                try {
                    Faker fake= new Faker();
                    switch (fieldType.getSimpleName()) {
                        case "String":
                            String fakerData = classHandler.generateRandomFieldFromFaker(field.getName());
                            String dataToInsert = fakerData.isEmpty() ? "Value for " + field.getName() + " " + i : fakerData;
                            field.set(product,dataToInsert);
                            break;
                        case "Double", "double" :
                            field.set(product, 20.0 + i);
                            break;
                        case "Integer", "int":
                            field.set(product, 100 - i);
                            break;
                        case "Boolean", "boolean":
                            field.set(product, i % 2 == 0); // Set true for even i, false for odd
                            break;
                        case "Long", "long":
                            field.set(product, 1000L + i);
                            break;
                        case "Float", "float":
                            field.set(product, 10.0f + i);
                            break;
                        case "Char", "char":
                            field.set(product, (char) ('A' + i));
                            break;
                        case "List":
                            field.set(product, new ArrayList<>()); // Initialize as an empty list
                            break;
                        case "LocalDate":
                            field.set(product, LocalDate.now().minusDays(i));
                            break;
                        default:
                            log.info("Unhandled field type: " + fieldType.getSimpleName());
                            break;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            products.add(product);
        }
        try {
            productRepository.saveAll(products);
        }catch (Exception e){
            log.info("data was inserted");
        }
    }
}
