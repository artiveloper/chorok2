package io.chorok2.modules.security;

import io.chorok2.modules.account.domain.Account;
import io.chorok2.modules.account.exception.SignInFailException;
import io.chorok2.modules.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        Account account;

        try {
            account = (Account) accountService.loadUserByUsername(email);

            if (!passwordEncoder.matches(password, account.getPassword())) {
                throw new BadCredentialsException("비밀번호를 확인해주세요.");
            }

        } catch (UsernameNotFoundException | BadCredentialsException exception) {
            throw new SignInFailException();
        }

        return new UsernamePasswordAuthenticationToken(email, password, account.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
