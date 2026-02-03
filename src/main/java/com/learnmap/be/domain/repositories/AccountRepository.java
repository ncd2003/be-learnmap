package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByActiveTrue();
    Optional<Account> findByEmailAndActiveTrue(String email);
    Optional<Account> findByIdAndActiveTrue(Long id);
    Optional<Account> findByPhoneNumberAndActiveTrue(String phoneNumber);
}
