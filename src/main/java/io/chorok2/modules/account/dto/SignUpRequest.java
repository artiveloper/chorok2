package io.chorok2.modules.account.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String email;
    private String password;
    private String nickname;

}
