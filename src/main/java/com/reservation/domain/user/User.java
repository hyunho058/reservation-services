package com.reservation.domain.user;

import com.reservation.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity(name = "USERS")
@Getter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Role role;

    protected User() {
    }

    public User(String userId,
                String userPw,
                String name,
                int age,
                String email,
                String phone,
                Role role) {
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public void updateInformation(String userPw, String name, int age) {
        this.userPw = userPw;
        this.name = name;
        this.age = age;
    }
}
