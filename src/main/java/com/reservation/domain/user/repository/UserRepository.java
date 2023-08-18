package com.reservation.domain.user.repository;

import com.reservation.domain.user.User;
import com.reservation.infrastructure.user.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
