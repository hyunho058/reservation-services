package com.reservation.domain.payment;

import com.reservation.domain.BaseTimeEntity;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer discountPrice;

    @Column
    private LocalDateTime canceledAt;

    protected Payment() {
    }

    public Payment(User user,
                   Performance performance,
                   Coupon coupon,
                   Integer price,
                   Integer discountPrice,
                   LocalDateTime canceledAt) {
        this.user = user;
        this.performance = performance;
        this.coupon = coupon;
        this.price = price;
        this.discountPrice = discountPrice;
        this.canceledAt = canceledAt;
    }
}
