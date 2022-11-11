package com.luanpaiva.todolistapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luanpaiva.todolistapi.domain.model.Authority;
import com.luanpaiva.todolistapi.domain.model.Role;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByRoleName(final Role roleName);
}
