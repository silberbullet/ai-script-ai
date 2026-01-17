package club.redis.properties.cache;

import lombok.extern.slf4j.Slf4j;
import club.redis.properties.cache.domain.DomainCacheProperties;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public record RedisCacheProperties(
        Map<String, DomainCacheProperties> domains
) {
    public RedisCacheProperties {
        if(domains == null || domains.isEmpty()) {
            log.warn("redis cache domains is empty");
        } else {
            domains = domains.entrySet().stream()
                    .collect(Collectors.toMap(
                            domain -> domain.getKey().strip(),
                            Map.Entry::getValue
                    ));
        }

    }
}
