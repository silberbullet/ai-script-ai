package club.product.rdb.flyway;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "product.flyway")
public record ProductFlywayProperties(
        String locations,
        String schema,
        boolean createSchemas,
        boolean baselineOnMigrate,
        boolean validateOnMigrate,
        boolean outOfOrder,
        String historyTable
) {}
