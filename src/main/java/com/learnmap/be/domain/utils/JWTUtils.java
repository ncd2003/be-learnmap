package com.learnmap.be.domain.utils;

import com.learnmap.be.domain.entities.Account;
import com.nimbusds.jose.util.Base64;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.learnmap.be.domain.dto.ResLoginDto;

import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class JWTUtils {
    private final JwtEncoder jwtEncoder;

    public JWTUtils(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    // Thuật toán dùng để mã hóa
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    // Lấy ra private key
    @Value("${jwt.private.key}")
    private String privateJwtKey;

    // Lấy ra thời gian access token hết hạn
    @Value("${jwt.access.token.expiration}")
    private long accessTokenExpiration;

    // Lấy ra thời gian refresh token hết hạn
    @Value("${jwt.refresh.token.expiration}")
    private long refreshTokenExpiration;

    // create a new access token
    public String createAccessToken(String email, ResLoginDto resLoginDto) {
        // Thông tin của account
        ResLoginDto.AccountLogin accountToken = new ResLoginDto.AccountLogin();
        accountToken.setAccountId(resLoginDto.getUserLogin().getAccountId());
        accountToken.setEmail(resLoginDto.getUserLogin().getEmail());
        accountToken.setFullName(resLoginDto.getUserLogin().getFullName());
        accountToken.setRole(resLoginDto.getUserLogin().getRole());
        accountToken.setPlan(resLoginDto.getUserLogin().getPlan());

        // Lấy thời gian hiện tại
        Instant now = Instant.now();
        // Tạo 1 biến lưu thời gian hết hạn của token bằng cách : tgian hiện tại  + thời gian hết hạn của token (accessTokenExpiration)
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);
        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                // Token được generation ra tại khi nào
                .issuedAt(now)
                // Set thời gian hết hạn cho token
                .expiresAt(validity)
                .subject(email)
                .claim("account", accountToken)
                .claim("authorities",List.of(resLoginDto.getUserLogin().getRole()))
                .claim("plan",resLoginDto.getUserLogin().getPlan())
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,
                claims)).getTokenValue();
    }

    // create a new refresh token
    public String refreshToken(String email, ResLoginDto resLoginDto) {
        // Thông tin của account
        ResLoginDto.AccountLogin accountToken = new ResLoginDto.AccountLogin();
        accountToken.setAccountId(resLoginDto.getUserLogin().getAccountId());
        accountToken.setEmail(resLoginDto.getUserLogin().getEmail());
        accountToken.setFullName(resLoginDto.getUserLogin().getFullName());
        // Lấy thời gian hiện tại
        Instant now = Instant.now();
        // Tạo 1 biến lưu thời gian hết hạn của token bằng cách : tgian hiện tại  + thời gian hết hạn của token (accessTokenExpiration)
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);
        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                // Token được generation ra tại khi nào
                .issuedAt(now)
                // Set thời gian hết hạn cho token
                .expiresAt(validity)
                .subject(email)
                .claim("account", accountToken)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,
                claims)).getTokenValue();
    }
    // Ghi đè lại phương thức getSecretKey để NimbusJwtEncoder biết mình đang sử dụng thuật toán HS512
    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(privateJwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length,
                JWTUtils.JWT_ALGORITHM.getName());
    }

    // check valid refresh token
    public Jwt checkValidRefreshToken(String refreshToken) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.
                withSecretKey(getSecretKey()).macAlgorithm(JWTUtils.JWT_ALGORITHM).build();
        try{
            return jwtDecoder.decode(refreshToken);
        }catch (Exception e) {
            System.out.println(">>> Refresh token error : "+e.getMessage());
            throw e;
        }
    }


    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    public Long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }
}

