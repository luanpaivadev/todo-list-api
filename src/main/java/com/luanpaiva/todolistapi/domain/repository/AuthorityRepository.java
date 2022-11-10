package com.luanpaiva.todolistapi.domain.repository;

import com.luanpaiva.todolistapi.domain.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByRoleName(final String roleName);

}
