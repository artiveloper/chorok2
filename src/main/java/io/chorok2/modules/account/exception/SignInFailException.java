package io.chorok2.modules.account.exception;

import io.chorok2.modules.error.BusinessException;

public class SignInFailException extends BusinessException {

    public SignInFailException() {
        super("signInFail");
    }

}
