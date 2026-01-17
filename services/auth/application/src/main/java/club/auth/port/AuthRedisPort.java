package club.auth.port;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public interface AuthRedisPort {
    
    void save(String key, String value, Duration ttl);
    
    void delete(String key);
    
    void deleteAll(List<String> keys);
    
    void addToSet(String userSetKey, String hashedRefreshToken);
    
    void removeFromSet(String userSetKey, String hashedRefreshToken);
    
    String get(String key);
    
    Boolean hasKey(String key);
    
    Set<String> getSetMembers(String userSetKey);
}
