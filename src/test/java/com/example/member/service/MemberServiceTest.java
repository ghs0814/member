package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.dto.request.MemberCreateRequest;
import com.example.member.dto.response.MemberResponse;
import com.example.member.exception.DuplicateEmailException;
import com.example.member.exception.MemberNotFoundException;
import com.example.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) // mock 기능 활성화
class MemberServiceTest {

    @Mock //의존되어지고 있는 객체
    MemberRepository memberRepository;

    @InjectMocks // 가짜 mock 주입, 지금 테스트하려는, 의존하고 있는 주체
    MemberService memberService;

    @Test
    void findMemberSuccess() {
        //given
        Member member = new Member("Kim", "kim@test.com");

        given(memberRepository.findById(1L))
                .willReturn(Optional.of(member));


        //when
        MemberResponse response = memberService.findById(1L);

        //assertJ
        assertThat(response.getName())
                .isEqualTo("Kim");
    }
    @Test
    void findMemberFailWhenMemberDoesNotExist() {

        given(memberRepository.findById(1L))
                .willReturn(Optional.empty()); // 예외발생

        assertThatThrownBy(() -> memberService.findById(1L)) //예외발생
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void createMemberSuccess() {

        Member member = new Member("Kim", "kim@test.com");

        given(memberRepository.existsByEmail("kim@test.com"))
                .willReturn(false);

        given(memberRepository.save(any(Member.class)))
                .willReturn(member);

        MemberCreateRequest request = new MemberCreateRequest(
                "Kim",
                "kim@test.com"
        );



        MemberResponse response = memberService.create(request);

        assertThat(response.getName()).isEqualTo("Kim");
        assertThat(response.getEmail()).isEqualTo("kim@test.com");
    }

    @Test
    void createMemberFailWhenEmailIsDuplicated() {

        given(memberRepository.existsByEmail("kim@test.com"))
                .willReturn(true);

        MemberCreateRequest request =
                new MemberCreateRequest("Kim", "kim@test.com");

        assertThatThrownBy(() -> memberService.create(request))
                .isInstanceOf(DuplicateEmailException.class);
    }
}