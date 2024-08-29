package data_dual.benchmark_sql.application;

import data_dual.benchmark_sql.domain.model.Product;
import data_dual.benchmark_sql.domain.service.ProductService;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String searchTerm) {
        List<Product> products = productService.searchProductsByCategory(searchTerm);
        return ResponseEntity.ok(products);
    }
    @GetMapping
    @Timed(value = "find.all.products.sql", description = "Time taken to find all products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
}
