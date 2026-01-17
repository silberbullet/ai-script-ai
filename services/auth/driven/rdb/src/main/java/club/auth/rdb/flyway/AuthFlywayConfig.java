package club.auth.rdb.flyway;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuthFlywayProperties.class)
public class AuthFlywayConfig {
    
    @Bean(initMethod = "migrate")
    public Flyway authFlyway(DataSource dataSource, AuthFlywayProperties props) {
        
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(props.locations())
                .schemas(props.schema())
                .defaultSchema(props.schema())
                .createSchemas(props.createSchemas())
                .baselineOnMigrate(props.baselineOnMigrate())
                .validateOnMigrate(props.validateOnMigrate())
                .outOfOrder(props.outOfOrder())
                .table(props.historyTable())
                .load();
    }
}
