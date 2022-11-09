package com.luanpaiva.todolistapi.domain.repository;

import com.luanpaiva.todolistapi.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(final String name);

}
