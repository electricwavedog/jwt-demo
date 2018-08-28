package com.example.jwtdemo.domain.repository;

import com.example.jwtdemo.domain.model.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liuyiqian
 */
public interface UserRepository extends JpaRepository<TUser, Long> {

    TUser findByUsername(String userName);
}
