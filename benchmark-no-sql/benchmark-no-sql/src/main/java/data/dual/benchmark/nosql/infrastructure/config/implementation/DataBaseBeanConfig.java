package data.dual.benchmark.nosql.infrastructure.config.implementation;

import data.dual.benchmark.nosql.infrastructure.config.OnInit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class DataBaseBeanConfig {
    @Value("${init.mode}")
    String initMode;

    @Qualifier("builder")
    private final OnInit databaseInitializer;
    @Qualifier("auto")
    private final OnInit databaseAutoInitializer;


    @Bean
    OnInit customDataBaseInitializer() {
        return switch (initMode) {
            case "auto" -> {
                databaseAutoInitializer.run();
                yield databaseAutoInitializer;
            }
            case "builder" -> {
                databaseInitializer.run();
                yield databaseInitializer;
            }
            default -> databaseInitializer;
        };
    }
}
