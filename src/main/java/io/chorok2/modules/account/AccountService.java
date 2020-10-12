package io.chorok2.modules.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(String email, String nickname) {
        Account account = Account.builder()
                .email(email)
                .nickname(nickname)
                .build();

        return accountRepository.save(account);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new Error("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public Account updateAccountInfo(Long id, String nickname) {
        Account updatedAccount = accountRepository.findById(id)
                .orElseThrow(() -> new Error("사용자를 찾을 수 없습니다."));

        updatedAccount.updateNickname(nickname);

        return updatedAccount;
    }

    @Transactional
    public void deleteAccount(Long id) {
        Account updatedAccount = accountRepository.findById(id)
                .orElseThrow(() -> new Error("사용자를 찾을 수 없습니다."));

        updatedAccount.updateDeleteFlag();
    }

}
