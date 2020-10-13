package io.chorok2.modules.account.exception;

public class SignInFailException extends RuntimeException {

    public SignInFailException() {
        super("signInFail");
    }
}
