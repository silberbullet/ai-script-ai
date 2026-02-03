package club.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import club.jwt.filter.annotation.AuthorizedUser;
import club.jwt.parser.JwtParser;
import club.jwt.parser.exception.JwtParsingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static club.jwt.parser.exception.JwtParserErrorCode.JWT_ACCESS_TOKEN_REQUIRED;

/**
 * JWT 인가 필터
 * HTTP 요청에서 JWT 토큰을 추출하고 검증하여 인가를 처리합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    
    private final JwtParser jwtParser;
    private final MethodPathPatternParser methodPathPatternParser;
    
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws IOException, ServletException {
        
        // ✅ CORS preflight(OPTIONS)는 인증 없이 통과해야 함
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            parseHeaderAndSetRequestAttribute(request);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우, 리프레시 토큰 요청 경로는 통과
            String requestURI = request.getRequestURI();
            if (requestURI.equals("/auth/token/refresh")) {
                var claims = e.getClaims();
                setRequestAttribute(request, claims);
                filterChain.doFilter(request, response);
            } else {
                log.warn("JWT 토큰이 만료되었습니다.");
                sendUnauthorizedResponse(request, response, "JWT 토큰이 만료되었습니다.", e.getMessage());
            }
        } catch (JwtException e) {
            log.error("JWT 토큰이 유효하지 않습니다.");
            sendUnauthorizedResponse(request, response, "JWT 토큰이 유효하지 않습니다.", e.getMessage());
        } catch (JwtParsingException e) {
            if (e.getErrorCode() != JWT_ACCESS_TOKEN_REQUIRED) {
                log.debug("에러 코드: {}", e.getErrorCode());
                throw e;
            }
            log.debug("JWT 토큰이 존재하지 않습니다.");
            sendUnauthorizedResponse(request, response, "JWT 토큰이 존재하지 않습니다.", "Authorization 헤더에 JWT 토큰이 존재하지 않습니다.");
        }
    }
    
    /**
     * JWT 토큰이 필요하지 않은 경로는 바로 통과
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // ✅ CORS preflight는 필터 제외
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;
        
        String requestURI = request.getRequestURI();
        PathContainer pathContainer = PathContainer.parsePath(requestURI);
        String method = request.getMethod();
        
        // ✅ shouldNotFilter는 "제외 경로 판정"만 담당해야 함 (토큰 파싱 제거)
        var patternSet = methodPathPatternParser.getExcludePathsByMethod(method);
        if (patternSet == null) return false;
        
        return patternSet.stream().anyMatch(pattern -> pattern.matches(pathContainer));
    }
    
    private void parseHeaderAndSetRequestAttribute(HttpServletRequest request)
            throws JwtException, JwtParsingException {
        String jwtToken = extractJwtToken(request);
        var claims = jwtParser.parseClaims(jwtToken);
        setRequestAttribute(request, claims);
    }
    
    private void setRequestAttribute(HttpServletRequest request, Claims claims) {
        @SuppressWarnings("unchecked")
        var authUser = new AuthorizedUser(
                claims.getSubject(),
                claims.get("roles", List.class),
                claims.get("profileIds", List.class)
        );
        request.setAttribute(AuthorizedUser.class.getTypeName(), authUser);
    }
    
    private String extractJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        
        return authorizationHeader.substring(7);
    }
    
    private void sendUnauthorizedResponse(HttpServletRequest request, HttpServletResponse response, String error, String message) throws IOException {
        if (response.isCommitted()) return;
        
        response.resetBuffer();
        
        String origin = request.getHeader("Origin");
        if (origin != null) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Vary", "Origin");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Expose-Headers", "*");
        }
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        
        byte[] body = new ObjectMapper().writeValueAsBytes(Map.of(
                "error", error,
                "message", message,
                "timestamp", Instant.now().toString()
        ));
        
        // ✅ chunked 대신 Content-Length 고정
        response.setContentLength(body.length);
        
        response.getOutputStream().write(body);
        response.flushBuffer();
    }
}
