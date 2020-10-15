package io.chorok2.modules.account.exception;

import io.chorok2.modules.error.BusinessException;

public class AlreadyUsedEmail extends BusinessException {

    public AlreadyUsedEmail(String message) {
        super("alreadyUsedEmail");
    }

}
