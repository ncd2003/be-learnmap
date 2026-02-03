package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqAccountDto;
import com.learnmap.be.domain.dto.ReqRegisterDto;
import com.learnmap.be.domain.dto.ReqSubscriptionDto;
import com.learnmap.be.domain.dto.ResAccountDto;
import com.learnmap.be.domain.entities.Account;
import com.learnmap.be.domain.entities.Plan;
import com.learnmap.be.domain.entities.Role;
import com.learnmap.be.domain.entities.Subscription;
import com.learnmap.be.domain.entities.type.RoleName;
import com.learnmap.be.domain.entities.type.UserStatus;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.MapperUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService extends BaseService {

    private final MapperUtils mapperUtils;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionService subscriptionService;

    public AccountService(MapperUtils mapperUtils, PasswordEncoder passwordEncoder, SubscriptionService subscriptionService) {
        super();
        this.mapperUtils = mapperUtils;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionService = subscriptionService;
    }

    public Account handleGetAccountByEmail(String email) {
        return accountRepository.findByEmailAndActiveTrue(email).orElseThrow(() -> new BadRequestException(Constants.ACCOUNT_NOT_FOUND));
    }

    public Account handleGetAccountById(Long id) {
        return accountRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new BadRequestException(Constants.ACCOUNT_NOT_FOUND));
    }

    public Account handleGetAccountByPhoneNumber(String phoneNumber) {
        return accountRepository.findByPhoneNumberAndActiveTrue(phoneNumber).orElseThrow(() -> new BadRequestException(Constants.ACCOUNT_NOT_FOUND));
    }

    public List<ResAccountDto> getAllAccounts() {
        return accountRepository.findAllByActiveTrue().stream().map(a -> mapperUtils.convertObjectToObject(a, ResAccountDto.class)).toList();
    }

    @Transactional
    public Account createAccount(ReqAccountDto reqAccountDto) {
        Account account = mapperUtils.convertObjectToObject(reqAccountDto, Account.class);
        if (accountRepository.findByEmailAndActiveTrue(reqAccountDto.getEmail()).isPresent()) {
            throw new BadRequestException("Email đã tồn tại");
        }
        if (accountRepository.findByPhoneNumberAndActiveTrue(reqAccountDto.getPhoneNumber()).isPresent()) {
            throw new BadRequestException("Số điện thoại đã tồn tại");
        }
        account.setPasswordHash(this.passwordEncoder.encode("123456"));
        Role roleDB = roleRepository.findByName(RoleName.valueOf(reqAccountDto.getRole().toString())).orElseThrow(() -> new BadRequestException(Constants.ROLE_NOT_FOUND));
        account.setRole(roleDB);
        accountRepository.save(account);
        subscriptionService.createSubscription(new ReqSubscriptionDto(account.getId(), 1L));
        return account;
    }

    // update refresh token
    public void updateAccountToken(String refreshToken, String email) {
        Account currentAccountDB = accountRepository.findByEmailAndActiveTrue(email).orElseThrow(() -> new BadRequestException(Constants.ACCOUNT_NOT_FOUND));
        if (currentAccountDB != null) {
            currentAccountDB.setRefreshToken(refreshToken);
            this.accountRepository.saveAndFlush(currentAccountDB);
        }
    }

    // Register account
    @Transactional
    public Account registerMember(ReqRegisterDto reqRegisterDto) {
        // convert reqRegisterDto to account
        Account account = mapperUtils.convertObjectToObject(reqRegisterDto, Account.class);

        // hash Password
        String hashPassword = this.passwordEncoder.encode(reqRegisterDto.getPassword());
        account.setPasswordHash(hashPassword);
        account.setStatus(UserStatus.ACTIVE);
        // role = member
        Role roleDB = roleRepository.findByName(RoleName.STUDENT).orElseThrow(() -> new BadRequestException(Constants.ROLE_NOT_FOUND));
        account.setRole(roleDB);
        accountRepository.save(account);
        subscriptionService.createSubscription(new ReqSubscriptionDto(account.getId(), 1L));
        return account;
    }

}
