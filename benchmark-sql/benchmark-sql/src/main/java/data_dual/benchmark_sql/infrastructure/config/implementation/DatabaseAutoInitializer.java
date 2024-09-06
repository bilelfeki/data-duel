package data_dual.benchmark_sql.infrastructure.config.implementation;

import com.github.javafaker.Faker;
import data_dual.benchmark_sql.domain.model.Product;
import data_dual.benchmark_sql.domain.service.ProductService;
import data_dual.benchmark_sql.infrastructure.ProductRepository;
import data_dual.benchmark_sql.infrastructure.config.OnDatabaseInit;
import data_dual.benchmark_sql.util.ClassHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("auto")
@Primary
public class DatabaseAutoInitializer implements OnDatabaseInit {

    @Value("${database.max-size}")
    private int maxSize;

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ClassHandler<?> classHandler ;

    public void run() {
        productRepository.deleteAll();

        // Cache field information to reduce reflection overhead
        Map<String, Field> fieldCache = Arrays.stream(Product.class.getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, field -> {
                    field.setAccessible(true);
                    return field;
                }));

        List<Product> products = IntStream.rangeClosed(1, maxSize)
                .parallel() // Use parallel stream for better performance
                .mapToObj(i -> {
                    Product product = new Product();
                    fieldCache.values().forEach(field -> {
                        Class<?> fieldType = field.getType();
                        try {
                            switch (fieldType.getSimpleName()) {
                                case "String":
                                    String fakerData = classHandler.generateRandomFieldFromFaker(field.getName());
                                    String dataToInsert = fakerData.isEmpty() ? "Value for " + field.getName() + " " + i : fakerData;
                                    field.set(product, dataToInsert);
                                    break;
                                case "Double", "double":
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
                    });
                    return product;
                })
                .collect(Collectors.toList());

        productService.saveProductsInBatches(products);
    }
}
