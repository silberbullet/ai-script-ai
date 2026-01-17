package club.auth.port;

public interface PasswordEncoderPort {
    
    String encode(CharSequence rawPassword);
    
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
