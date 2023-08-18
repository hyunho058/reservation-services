package com.reservation.domain.payment.repository;

import com.reservation.domain.payment.Coupon;
import com.reservation.infrastructure.payment.custom.CouponRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
}
