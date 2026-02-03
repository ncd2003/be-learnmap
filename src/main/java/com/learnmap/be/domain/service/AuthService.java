package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqLoginDto;
import com.learnmap.be.domain.dto.ReqRegisterDto;
import com.learnmap.be.domain.dto.ResLoginDto;
import com.learnmap.be.domain.entities.Account;
import com.learnmap.be.domain.entities.Subscription;
import com.learnmap.be.domain.entities.type.SubscriptionStatus;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.JWTUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService extends BaseService {
    AuthenticationManagerBuilder authenticationManager;
    JWTUtils jwtUtils;
    private AccountService accountService;

    public ResponseEntity<ResLoginDto> login(ReqLoginDto reqLoginDTO) {
        // 1. Nap input gồm username và password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(reqLoginDTO.getEmail(), reqLoginDTO.getPassword());

        // 2. Xác minh tài khoản
        // - Cần ghi đè lại method loadUserByUsername bởi vì mặc định là sẽ check trong memory
        Authentication authentication = authenticationManager.getObject().authenticate(authenticationToken);

        // 4. Lưu user vào Context -> Lấy ra user hiện đang thao tác
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get currAccount
        Account currAccountDB = accountService.handleGetAccountByEmail(reqLoginDTO.getEmail());

        Subscription subscriptionDB = subscriptionRepository.findSubscriptionByAccount_IdAndSubscriptionStatus(currAccountDB.getId(), SubscriptionStatus.ACTIVE).orElseThrow(() -> new BadRequestException(Constants.SUBSCRIPTION_NOT_FOUND));
        ResLoginDto resLoginDTO = new ResLoginDto();
        ResLoginDto.AccountLogin accountLogin = new ResLoginDto.AccountLogin(currAccountDB.getId(), currAccountDB.getEmail(), currAccountDB.getFullName(), currAccountDB.getRole().getName().toString(), subscriptionDB.getPlan().getCode());

        // set into resLoginDTO
        resLoginDTO.setUserLogin(accountLogin);

        // 5. Tạo token
        String accessToken = this.jwtUtils.createAccessToken(authentication.getName(), resLoginDTO);
        resLoginDTO.setAccessToken(accessToken);

        // create refresh token
        String refreshToken = this.jwtUtils.refreshToken(reqLoginDTO.getEmail(), resLoginDTO);

        // Update refresh token
        this.accountService.updateAccountToken(refreshToken, reqLoginDTO.getEmail());

        // Set cookie
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtUtils.getRefreshTokenExpiration())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(resLoginDTO);
    }

    @Transactional
    public Account register(ReqRegisterDto reqRegisterDTO) {
        // check exist phone
        if (accountRepository.findByEmailAndActiveTrue(reqRegisterDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email đã tồn tại");
        }
        if (accountRepository.findByPhoneNumberAndActiveTrue(reqRegisterDTO.getPhoneNumber()).isPresent()) {
            throw new BadRequestException("Số điện thoại đã tồn tại");
        }
        // check password and confirm password
        if (!reqRegisterDTO.getPassword().equals(reqRegisterDTO.getConfirmPassword())) {
            throw new BadRequestException(Constants.PASSWORD_MISMATCH);
        }
        return accountService.registerMember(reqRegisterDTO);
    }

//    public ResLoginDTO.AccountLogin getAccount() {
//        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
//        Account currAccountDB = accountService.handleGetAccountByEmail(email);
//        ResLoginDTO.AccountLogin accountLogin = new ResLoginDTO.AccountLogin(currAccountDB.getAccountId(), currAccountDB.getEmail(), currAccountDB.getFullName(), currAccountDB.getStatus(), currAccountDB.getRole());
//        return ResponseEntity.ok().body(accountLogin);
//    }
//
//    // refresh token
//    @GetMapping("/refresh")
//    @APIMessage("Get user by refresh token")
//    public ResponseEntity<ResLoginDTO> getRefreshToken(@CookieValue(name = "refresh_token", defaultValue = "missing_refresh_token") String refresh_token) {
//        if ("missing_refresh_token".equals(refresh_token)) {
//            throw new IdInvalidException("Refresh token is missing");
//        }
//        // 1. check valid refresh_token by NimbusJWTDecoder
//        Jwt decodedRefreshToken = this.securityUtil.checkValidRefreshToken(refresh_token);
//
//        // get email by sub
//        String email = decodedRefreshToken.getSubject();
//
//        // 2. check user by refresh token + email
//        Account accountDB = accountService.getUserByRefreshTokenAndEmail(refresh_token, email);
//        if (accountDB == null) {
//            throw new IdInvalidException("Invalid refresh token");
//        }
//
//        // issue new token/set refresh token as cookies
//        ResLoginDTO resLoginDTO = new ResLoginDTO();
//        ResLoginDTO.AccountLogin accountLogin = new ResLoginDTO.AccountLogin(accountDB.getAccountId(), email, accountDB.getFullName(), accountDB.getStatus(), accountDB.getRole());
//        // set into resLoginDTO
//        resLoginDTO.setUserLogin(accountLogin);
//
//        // 3. Tạo token
//        String accessToken = this.securityUtil.createAccessToken(email, resLoginDTO);
//        resLoginDTO.setAccessToken(accessToken);
//
//        // create refresh token
//        String new_refresh_Token = this.securityUtil.refreshToken(email, resLoginDTO);
//
//        // Update refresh token
//        this.accountService.updateAccountToken(new_refresh_Token, email);
//
//        // Set cookie
//        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", new_refresh_Token)
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(refreshTokenExpiration)
//                .build();
//
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(resLoginDTO);
//    }
//
//    @PostMapping("/logout")
//    @APIMessage("Logout user")
//    public ResponseEntity<Void> logout() {
//        // Find current user
//        String email = SecurityUtil.getCurrentUserLogin().orElse(null);
//        // update refresh token = null
//        this.accountService.updateAccountToken(null, email);
//        // Set cookie with max age = 0
//        ResponseCookie deleteSpringCookie = ResponseCookie.from("refresh_token", "")
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(0) // This deletes the cookie
//                .build();
//
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString()).body(null);
//    }
}
