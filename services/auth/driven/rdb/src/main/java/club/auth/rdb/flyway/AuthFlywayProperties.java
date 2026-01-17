package club.auth.rdb.flyway;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.flyway")
public record AuthFlywayProperties(
        String locations,
        String schema,
        boolean createSchemas,
        boolean baselineOnMigrate,
        boolean validateOnMigrate,
        boolean outOfOrder,
        String historyTable
) {}
