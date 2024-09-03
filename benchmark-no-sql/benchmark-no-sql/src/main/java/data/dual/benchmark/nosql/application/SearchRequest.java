package data.dual.benchmark.nosql.application;

import lombok.Builder;

@Builder
public record SearchRequest(String keyword, String category, Double minPrice, Double maxPrice, String brand) {
}

