package io.chorok2.modules.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileResponse {

    private Long accountId;
    private String email;
    private List<String> roles;

}
