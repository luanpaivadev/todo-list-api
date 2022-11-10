package com.luanpaiva.todolistapi.domain.repository;

import com.luanpaiva.todolistapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(final String username);

}
