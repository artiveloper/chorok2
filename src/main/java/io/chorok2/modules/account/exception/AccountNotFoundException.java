package io.chorok2.modules.account.exception;

import io.chorok2.modules.error.EntityNotFoundException;

public class AccountNotFoundException extends EntityNotFoundException {

    public AccountNotFoundException() {
        super("accountNotFound");
    }

}
