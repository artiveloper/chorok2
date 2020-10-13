package io.chorok2.modules.account.dto;

import lombok.Data;

@Data
public class SignInRequest {

    private String email;
    private String password;

}
