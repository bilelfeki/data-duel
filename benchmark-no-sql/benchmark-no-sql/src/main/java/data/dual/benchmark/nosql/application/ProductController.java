package data.dual.benchmark.nosql.application;

import data.dual.benchmark.nosql.domain.model.Product;
import data.dual.benchmark.nosql.domain.service.ProductService;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Timed(value = "search.products.query.nosql", description = "Time taken to search for products")
    public ResponseEntity<List<Product>> searchProducts(@RequestBody SearchRequest searchRequest) {
        List<Product> products = productService.search(searchRequest);
        return ResponseEntity.ok(products);
    }
    @GetMapping
    @Timed(value = "find.all.products.nosql", description = "Time taken to find all products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
}

