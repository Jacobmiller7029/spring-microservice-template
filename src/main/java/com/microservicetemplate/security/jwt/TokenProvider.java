package com.microservicetemplate.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Slf4j
@Component
public class TokenProvider {

    private final String secretKey;
    private final long tokenLifeInSeconds;
    private final UserService userService;

    public TokenProvider(UserService userService,
                         @Value("${lanetracker.secret}") String secret,
                         @Value("${lanetracker.tokenDurationMilliseconds}") Long tokenDurationMilliseconds ) {
        this.secretKey = Base64.getEncoder().encodeToString(secret.getBytes());
        this.tokenLifeInSeconds = 1000 * tokenDurationMilliseconds;
        this.userService = userService;
    }

    public String createToken(String username) {
        log.info("Creating token...");

        return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS512, this.secretKey)
                .setExpiration(generateTokenExpirationDate()).compact();
    }

    public Date generateTokenExpirationDate() {
        Date now = new Date();
        return new Date(now.getTime() + this.tokenLifeInSeconds);
    }

    public Authentication getAuthentication(String token) {

        log.info("getting authentication...", token);

        String username = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token)
                .getBody().getSubject();

        log.info("retrieved username: {}", username);

        UserDetails userDetails = this.userService.loadUserByUsername(username);

        log.info("getting user details... ", userDetails);

        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }
}
