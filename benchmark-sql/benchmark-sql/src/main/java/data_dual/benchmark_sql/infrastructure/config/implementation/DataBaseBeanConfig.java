package data_dual.benchmark_sql.infrastructure.config.implementation;

import data_dual.benchmark_sql.infrastructure.config.OnDatabaseInit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataBaseBeanConfig {
    @Value("${database.init.mode}")
    String initMode;

    @Qualifier("builder")
    private final OnDatabaseInit databaseInitializer;
    @Qualifier("auto")
    private final OnDatabaseInit databaseAutoInitializer;


    @Bean
    OnDatabaseInit customDataBaseInitializer() {
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
