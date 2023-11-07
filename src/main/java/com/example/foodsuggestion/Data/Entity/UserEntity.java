package com.example.foodsuggestion.Data.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_user;

    @Column(name = "name_user")
    private String name_user;

    private String passwd_user; // 평문 비밀번호 필드

    private boolean allergy_user;

    private String allergy_info;
}