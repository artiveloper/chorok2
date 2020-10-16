package io.chorok2.modules.account.validator;

import io.chorok2.infra.yml.MessageSourceUtil;
import io.chorok2.modules.account.dto.SignUpRequest;
import io.chorok2.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidation implements Validator {

    private final AccountRepository accountRepository;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest signUpRequest = (SignUpRequest) target;
        String email = signUpRequest.getEmail();

        boolean existsByEmail = accountRepository.existsByEmail(email);

        if (existsByEmail) {
            errors.rejectValue("email", "ExistsEmail", messageSourceUtil.getMessage("ExistsEmail.message"));
        }
    }

}
