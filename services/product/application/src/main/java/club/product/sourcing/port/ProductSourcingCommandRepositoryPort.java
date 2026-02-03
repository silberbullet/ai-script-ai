package club.product.sourcing.port;

import club.product.sourcing.domain.ProductSourcing;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface ProductSourcingCommandRepositoryPort {
    
    <T> T transaction(Supplier<T> supplier);
    
    List<ProductSourcing> saveAll(List<ProductSourcing> sourcingList);
    
    ProductSourcing update(String id, ProductSourcing productSourcing);
    
    void deleteById(String id);
    
    Optional<ProductSourcing> findById(String id);
}
