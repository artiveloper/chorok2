package io.chorok2.modules.account.exception;

import io.chorok2.modules.error.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {

    public RoleNotFoundException() {
        super("roleNotFound");
    }

}
