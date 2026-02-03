package club.product.sourcing.exception;

import club.common.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.function.Supplier;

public enum ProductErrorCode implements ErrorCode {
    USER_NOT_FOUND("상품 소싱 데이터 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SOURCING_NOT_FOUND("상품 소싱 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SOURCING_FORBIDDEN("해당 상품 소싱 데이터에 접근할 수 없습니다.", HttpStatus.FORBIDDEN),
    SOURCING_NAME_REQUIRED("상품명은 필수입니다.", HttpStatus.BAD_REQUEST),
    INVALID_DATE_RANGE("조회 시작일(from)은 종료일(to)보다 이전이어야 합니다.", HttpStatus.BAD_REQUEST),
    INVALID_BULK_REQUEST("전제 저장 처리 시 유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST);
   
    private final String message;
    private final HttpStatus httpStatus;

    ProductErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public ProductException exception() {
        return new ProductException(this);
    }

    @Override
    public ProductException exception(Throwable cause) {
        return new ProductException(this, cause);
    }

    @Override
    public RuntimeException exception(Runnable runnable) {
        return new ProductException(this, runnable);
    }

    @Override
    public RuntimeException exception(Runnable runnable, Throwable cause) {
        return new ProductException(this, runnable, cause);
    }

    @Override
    public RuntimeException exception(Supplier<Map<String, Object>> payload) {
        return new ProductException(this, payload);
    }

    @Override
    public RuntimeException exception(Supplier<Map<String, Object>> payload, Throwable cause) {
        return new ProductException(this, payload, cause);
    }
}