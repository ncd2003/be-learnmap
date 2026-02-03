package com.learnmap.be.domain.security.service;

import com.learnmap.be.domain.entities.Account;
import com.learnmap.be.domain.exception.WrongUserOrPasswordException;
import com.learnmap.be.domain.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailService")
@AllArgsConstructor
public class UserDetailsCustom implements UserDetailsService {
    private final AccountService accountService;

    // 3. Ghi đè lại method loadUserByUsername trong class UserDetailsService để check account ở trong database chứ không phải mặc định là check trong memory
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.handleGetAccountByEmail(email);
        // Kiểm tra xem username có tồn tại trong db không?
        if (account == null) {
            throw new WrongUserOrPasswordException("Thông tin không hợp lệ");
        }
        return new User(account.getEmail(),
                account.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority(account.getRole().getName().toString())));
    }
}
