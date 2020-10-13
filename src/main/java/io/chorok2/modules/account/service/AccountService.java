package io.chorok2.modules.account.service;

import io.chorok2.modules.account.domain.Account;
import io.chorok2.modules.account.dto.SignInRequest;
import io.chorok2.modules.account.exception.AccountNotFoundException;
import io.chorok2.modules.account.dto.SignUpRequest;
import io.chorok2.modules.account.exception.SignInFailException;
import io.chorok2.modules.account.repository.AccountRepository;
import io.chorok2.modules.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Account createAccount(SignUpRequest signUpRequest) {

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        Account account = Account.builder()
                .email(signUpRequest.getEmail())
                .nickname(signUpRequest.getNickname())
                .password(encodedPassword)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        return accountRepository.save(account);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    public Account updateAccountInfo(Long id, String nickname) {
        Account updatedAccount = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);

        updatedAccount.updateNickname(nickname);

        return updatedAccount;
    }

    @Transactional
    public void deleteAccount(Long id) {
        Account updatedAccount = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);

        updatedAccount.updateDeleteFlag();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(AccountNotFoundException::new);
    }

    public UserDetails loadUserByUserId(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

}
