package io.chorok2.modules.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpRequest {

    @Email
    @NotBlank(message = "{EmailNotBlank.message}")
    private String email;

    @NotBlank(message = "비밀번호는 공백이여서는 안됩니다요")
    private String password;

    @NotBlank(message = "닉네임은 필수값입니다.")
    private String nickname;

}
