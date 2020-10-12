package io.chorok2.modules.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private boolean isDeleted;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateDeleteFlag() {
        this.isDeleted = true;
    }

}
