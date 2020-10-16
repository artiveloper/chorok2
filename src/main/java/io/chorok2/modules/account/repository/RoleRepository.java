package io.chorok2.modules.account.repository;

import io.chorok2.modules.account.domain.Role;
import io.chorok2.modules.account.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}
