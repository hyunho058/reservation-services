package com.reservation.domain.payment;

import com.reservation.domain.BaseTimeEntity;
import com.reservation.domain.performance.Performance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Coupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Performance performance;

    @Column(nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private Integer discountValue;

    @Column
    private LocalDateTime usedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;


    public Coupon(Performance performance,
                  String serialNumber,
                  Type type,
                  Integer discountValue,
                  LocalDateTime expiredAt) {
        this(null,
                performance,
                serialNumber,
                type,
                discountValue,
                null,
                expiredAt);
    }

    public enum Type {
        PERCENT,
        WON
    }

    public boolean isOverDiscountValue(int value) {
        if (Type.PERCENT.equals(this.type)) {
            return value > 100;
        }
        if (Type.WON.equals(this.type)) {
            return value > this.performance.getPrice();
        }
        return false;
    }

    public Coupon delete() {
        this.expiredAt = LocalDateTime.now();
        return this;
    }

    public Coupon update(Performance performance,
                         Type type,
                         Integer value,
                         LocalDateTime expiredAt) {
        this.performance = performance;
        this.type = type;
        this.discountValue = value;
        this.expiredAt = expiredAt;
        return this;
    }

    public Coupon use(LocalDateTime useTime) {
        this.usedAt = useTime;
        return this;
    }

    protected Coupon() {
    }

    public Coupon(Long id, Performance performance, String serialNumber, Type type, Integer discountValue, LocalDateTime usedAt, LocalDateTime expiredAt) {
        this.id = id;
        this.performance = performance;
        this.serialNumber = serialNumber;
        this.type = type;
        this.discountValue = discountValue;
        this.usedAt = usedAt;
        this.expiredAt = expiredAt;
    }
}
