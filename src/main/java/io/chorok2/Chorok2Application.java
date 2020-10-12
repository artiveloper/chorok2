package io.chorok2;

import io.chorok2.modules.account.Account;
import io.chorok2.modules.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class Chorok2Application implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(Chorok2Application.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Account account = Account.builder()
                .email("onepoint@gmail.com")
                .nickname("onepoint")
                .build();

        Account account2 = Account.builder()
                .email("onepoint@gmail.com")
                .nickname("onepoint")
                .build();

        List<Account> accounts = Arrays.asList(account, account2);
        accountRepository.saveAll(accounts);
    }

}
