package data_dual.benchmark_sql.infrastructure.config;

import data_dual.benchmark_sql.domain.model.Product;
import data_dual.benchmark_sql.infrastructure.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertConfig /* implements CommandLineRunner */{

    @Value("${database.max-size}")
    private int dbMaxSize;

    private final ProductRepository productRepository;

    public InsertConfig(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        productRepository.deleteAll();
//
//        List<Product> products = new ArrayList<>();
//
//        for (int i = 1; i <= dbMaxSize; i++) {
//            for (int j = 1; j <= 10; j++) {
//                Product product = new Product();
//                for (Field field : Product.class.getDeclaredFields()) {
//                    field.setAccessible(true);
//                    Class<?> fieldType = field.getType();
//                    try {
//                        switch (fieldType.getSimpleName()) {
//                            case "String":
//                                field.set(product, "Value for " + field.getName() + " " + j);
//                                break;
//                            case "Double", "double":
//                                field.set(product, 20.0 + i);
//                                break;
//                            case "Integer", "int":
//                                field.set(product, 100 - i);
//                                break;
//                            case "Boolean", "boolean":
//                                field.set(product, i % 2 == 0); // Set true for even i, false for odd
//                                break;
//                            case "Long", "long":
//                                field.set(product, 1000L + i);
//                                break;
//                            case "Float", "float":
//                                field.set(product, 10.0f + i);
//                                break;
//                            case "Char", "char":
//                                field.set(product, (char) ('A' + j));
//                                break;
//                            case "List":
//                                field.set(product, new ArrayList<>()); // Initialize as an empty list
//                                break;
//                            case "LocalDate":
//                                field.set(product, LocalDate.now().minusDays(j));
//                                break;
//                            default:
//                                System.out.println("Unhandled field type: " + fieldType.getSimpleName());
//                                break;
//                        }
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                products.add(product);
//            }
//        }
//        try {
//            productRepository.saveAll(products);
//        }catch (Exception e){
//            System.out.println("data was inserted");
//        }
//    }
}
