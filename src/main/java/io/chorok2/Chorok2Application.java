package io.chorok2;

import io.chorok2.modules.account.domain.Account;
import io.chorok2.modules.account.domain.Role;
import io.chorok2.modules.account.domain.RoleName;
import io.chorok2.modules.account.exception.RoleNotFoundException;
import io.chorok2.modules.account.repository.AccountRepository;
import io.chorok2.modules.account.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class Chorok2Application implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Chorok2Application.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role roleUser = Role.builder()
                .name(RoleName.ROLE_USER)
                .build();

        Role roleAdmin = Role.builder()
                .name(RoleName.ROLE_ADMIN)
                .build();

        roleRepository.saveAll(Arrays.asList(roleUser, roleAdmin));

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(RoleNotFoundException::new);

        Account account = Account.builder()
                .email("onepoint@gmail.com")
                .nickname("onepoint")
                .password(passwordEncoder.encode("1234"))
                .roles(Collections.singleton(role))
                .build();

        Account account2 = Account.builder()
                .email("artiveloper@gmail.com")
                .nickname("artiveloper")
                .password(passwordEncoder.encode("1234"))
                .roles(Collections.singleton(role))
                .build();

        List<Account> accounts = Arrays.asList(account, account2);
        accountRepository.saveAll(accounts);
    }

}
