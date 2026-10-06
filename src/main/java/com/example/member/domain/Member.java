package com.example.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDateTime createdAt;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
        this.status = MemberStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void withdraw() {
        this.status = MemberStatus.WITHDRAWN;
    }
}
