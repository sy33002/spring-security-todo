package sesac.springsecuritytodo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter
    // - 한 요청당 한 번 실행됨

    @Autowired
    private TokenProvider tokenProvider;

    // doFilterInternal 메소드
    // - OncePerRequestFilter 에 정의된 추상 메소드 중 하나
    // - 재정의한 메소드에서 하는 작업: JWT 토큰 검증, 사용자 정보를 Spring Security 의 SecurityContextHolder 에 등록
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.info("Filter is running...");
            String token = parseBearerToken(request);

            // token 검사
            // - 토큰 인증 부분 구현
            // - 유효시간 검사 생략
            if (token != null && !token.equalsIgnoreCase("null")) {
                // 토큰이 null, "null" 이 아니라면 토큰 검사 진행

                // userId 가져오기 (만약 토큰이 위조되었다면 예외 처리)
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID: " + userId);

                // 인증 완료 -> SecurityContextHolder 에 등록되어야 인증된 사용자!
                AbstractAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES); // 사용자 정보
                authentication.setDetails((new WebAuthenticationDetailsSource().buildDetails(request))); // 사용자 인증 세부 정보 설정

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // 빈 SecurityContext 생성
                securityContext.setAuthentication(authentication); // context에 인증 정보 설정
                SecurityContextHolder.setContext(securityContext); // SecurityContextHolder 저장
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        // 다음 필터로 계속 진행
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // 요청의 헤더에서 Bearer 토큰을 가져옴
        String bearerToken = request.getHeader("Authorization");

        // 토큰 파싱
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Bearer 6글자 + 공백 1글자
        }

        return null;
    }
}

// doFilterInternal 메소드 사용법 (시그니처)
// - 요청을 가로채서 필요한 작업을 수행한 다음에 다음 필터로 요청 전달
/*
protected void doFilterInternal(
  HttpServletRequest req,
  HttpServletResponse res,
  FilterChain filterChain
) throws ServletException, IOException {
  // 해당 필터에서 필요한 작업
  // ...

  // 다음 필터로 요청 전달
  filterChain.doFilter(req, res);
}
*/