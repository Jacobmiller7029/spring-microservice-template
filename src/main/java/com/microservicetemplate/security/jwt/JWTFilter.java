package com.microservicetemplate.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Filters incoming requests and installs a Spring Security principal if a header
 * corresponding to a valid user is found.
 */
@Slf4j
@Component
public class JWTFilter extends GenericFilterBean {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final ObjectMapper mapper;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        log.info("do filter request firing...");

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String jwt = resolveToken(httpServletRequest);
            if (jwt != null) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | SignatureException | UsernameNotFoundException e) {
            log.info("JWTFilter Security exception: {}, <> Exception: {}", e.getMessage(), e.getClass().toString());
            ((HttpServletResponse) servletResponse)
                    .setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse
                    .setContentType("application/json");
            servletResponse.getWriter()
                    .write(mapper.writeValueAsString(new InvalidTokenResponse(e.getMessage())));
            servletResponse.getWriter().flush();
        }
    }

    private static String resolveToken(HttpServletRequest request) {

        log.info("Resolving token from request: {}", request);

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}