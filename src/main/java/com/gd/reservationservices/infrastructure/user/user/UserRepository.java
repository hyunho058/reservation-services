package com.gd.reservationservices.infrastructure.user.user;

import com.gd.reservationservices.domain.user.User;
import com.gd.reservationservices.infrastructure.user.user.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
