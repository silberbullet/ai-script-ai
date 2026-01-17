package club.hibenate.generator;

import club.snowflake.persistence.id.Snowflake;
import club.snowflake.properties.SnowflakeProperties;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SnowflakeIdGenerator implements IdentifierGenerator {
    
    private final Snowflake snowflake;
    
    public SnowflakeIdGenerator(SnowflakeProperties snowflakeProperties) {
        this.snowflake = new Snowflake(snowflakeProperties);
    }
    
    @Override
    public Long generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return snowflake.nextId();
    }
}
