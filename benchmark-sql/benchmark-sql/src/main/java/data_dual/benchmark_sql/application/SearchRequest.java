package data_dual.benchmark_sql.application;

import lombok.Builder;

@Builder
public record SearchRequest(String keyword, String category, Double minPrice, Double maxPrice, String brand) {
}
