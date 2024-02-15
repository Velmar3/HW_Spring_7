package ru.gb.Application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Application.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
