package club.properties;

import club.properties.allowed.CorsAllowedProperties;
import club.properties.exposed.CorsExposedProperties;

public record MappedCorsProperties(
        String path,
        CorsAllowedProperties allowed,
        CorsExposedProperties exposed,
        Long maxAge
) {
    public MappedCorsProperties {
        if (maxAge == null) {
            maxAge = 3600L;
        }
    }
}
