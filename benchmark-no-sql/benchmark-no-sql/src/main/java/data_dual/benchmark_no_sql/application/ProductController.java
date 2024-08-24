package data_dual.benchmark_no_sql.application;

import data_dual.benchmark_no_sql.domain.model.Product;
import data_dual.benchmark_no_sql.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String category) {
        List<Product> products = productService.searchProductsByCategory(category);
        return ResponseEntity.ok(products);
    }
}

