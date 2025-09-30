package com.elim.server.gas_monitoring.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.elim.server.gas_monitoring.common.util.IpUtil.getClientIp;


@Slf4j
@Order(1)
@Component
public class LogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        // 캐싱 가능한 래퍼로 요청 감싸기
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        String ip = getClientIp(wrappedRequest);
        int port = wrappedRequest.getRemotePort();

        // 요청 정보
        String method = wrappedRequest.getMethod();
        String uri = wrappedRequest.getRequestURI();

        // 로그 출력
        log.info("✅ Incoming Request → [{}] {}", method, uri);
        log.info("Client IP={}", ip);
//        log.info("Client Port={}", port);

        // 체인 실행 (body가 이 시점에 읽혀야 캐싱됨)
        filterChain.doFilter(wrappedRequest, response);
    }


    private void logResponse(ContentCachingResponseWrapper response) {
        int status = response.getStatus();
        String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

        System.out.println("✅ Outgoing Response");
        System.out.println("▶ Result: " + status);
        System.out.println("▶ Body: " + body);
    }
}
