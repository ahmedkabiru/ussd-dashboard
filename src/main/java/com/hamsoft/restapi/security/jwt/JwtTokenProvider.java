package com.hamsoft.restapi.security.jwt;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static  final String ROLES_KEY = "roles";
    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.token-validity-in-seconds}")
    private long tokenValidityInMilliseconds;

    @Value("${app.jwt.token-validity-in-seconds-for-remember-me}")
    private long tokenValidityInMillisecondsForRememberMe;



    public String createToken(Authentication authentication , boolean rememberMe){
        String authorities = authentication.getAuthorities().stream()
                             .map(GrantedAuthority::getAuthority)
                             .collect(Collectors.joining(","));
        long now  = (new Date()).getTime();
        Date validity;
        if(rememberMe){
            validity = new Date(now  + (this.tokenValidityInMillisecondsForRememberMe * 1000));
        }else {
            validity = new Date(now + (this.tokenValidityInMilliseconds * 1000));
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(ROLES_KEY,authorities)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(ROLES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

}
