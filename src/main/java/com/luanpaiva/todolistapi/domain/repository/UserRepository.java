package com.luanpaiva.todolistapi.domain.repository;

import com.luanpaiva.todolistapi.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(final String username);

}
