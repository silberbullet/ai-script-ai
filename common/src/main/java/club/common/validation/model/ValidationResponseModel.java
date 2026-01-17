package club.common.validation.model;

import lombok.Builder;
import club.common.validation.model.interfaces.BaseValidationProperty;

import java.time.Instant;
import java.util.Map;

@Builder
public record ValidationResponseModel(
        Map<String, BaseValidationProperty> validations,
        Instant serverTime,
        Instant lastUpdatedAt
) {
    public ValidationResponseModel {
        if (serverTime == null) {
            serverTime = Instant.now();
        }
    }
}
