package club.product.sourcing.exception;

import club.common.CustomException;

import java.util.Map;
import java.util.function.Supplier;

public class ProductException extends CustomException {

    public ProductException(ProductErrorCode errorCode) {
        super(errorCode);
    }

    public ProductException(ProductErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ProductException(ProductErrorCode errorCode, Runnable runnable) {
        super(errorCode, runnable);
    }

    public ProductException(ProductErrorCode errorCode, Runnable runnable, Throwable cause) {
        super(errorCode, runnable, cause);
    }

    public ProductException(ProductErrorCode errorCode, Supplier<Map<String, Object>> payload) {
        super(errorCode, payload);
    }

    public ProductException(ProductErrorCode errorCode, Supplier<Map<String, Object>> payload, Throwable cause) {
        super(errorCode, payload, cause);
    }
}
