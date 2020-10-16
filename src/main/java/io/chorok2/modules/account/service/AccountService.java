package io.chorok2.modules.account.service;

import io.chorok2.modules.account.domain.Account;
import io.chorok2.modules.account.domain.Role;
import io.chorok2.modules.account.domain.RoleName;
import io.chorok2.modules.account.dto.ProfileResponse;
import io.chorok2.modules.account.dto.SignInRequest;
import io.chorok2.modules.account.exception.AccountNotFoundException;
import io.chorok2.modules.account.dto.SignUpRequest;
import io.chorok2.modules.account.exception.RoleNotFoundException;
import io.chorok2.modules.account.exception.SignInFailException;
import io.chorok2.modules.account.repository.AccountRepository;
import io.chorok2.modules.account.repository.RoleRepository;
import io.chorok2.modules.security.AccountDetails;
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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Account createAccount(SignUpRequest signUpRequest) {
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(RoleNotFoundException::new);

        Account account = Account.builder()
                .email(signUpRequest.getEmail())
                .nickname(signUpRequest.getNickname())
                .password(encodedPassword)
                .roles(Collections.singleton(role))
                .build();

        return accountRepository.save(account);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public ProfileResponse getProfile(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);

        return ProfileResponse
                .builder()
                .accountId(account.getId())
                .email(account.getEmail())
                .roles(account.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                .build();
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
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(AccountNotFoundException::new);
        return new AccountDetails(account);
    }

    public UserDetails loadUserByUserId(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        return new AccountDetails(account);
    }

}
