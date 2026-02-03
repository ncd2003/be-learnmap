package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqAccountDto;
import com.learnmap.be.domain.dto.ResAccountDto;
import com.learnmap.be.domain.entities.Account;
import com.learnmap.be.domain.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping
    public List<ResAccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Account createAccount(@Valid @RequestBody ReqAccountDto reqAccountDto) {
        return accountService.createAccount(reqAccountDto);
    }

}
