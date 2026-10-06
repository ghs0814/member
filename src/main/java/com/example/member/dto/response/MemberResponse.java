package com.example.member.dto.response;

import com.example.member.domain.Member;
import com.example.member.domain.MemberStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final MemberStatus status;
    private final LocalDateTime createdAt;

    public MemberResponse(
            Long id,
            String name,
            String email,
            MemberStatus status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getStatus(),
                member.getCreatedAt()
        );
    } // 편하게 생성하기 위해 사용
}