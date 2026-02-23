package club.batch.config;

import club.batch.properties.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BatchProperties.class)
public class BatchApiConfig {
}
