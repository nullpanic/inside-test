package dev.nullpanic.insidetest.persist.repositories;

import dev.nullpanic.insidetest.persist.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
