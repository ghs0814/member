package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.dto.request.MemberCreateRequest;
import com.example.member.dto.request.MemberUpdateRequest;
import com.example.member.dto.response.MemberResponse;
import com.example.member.exception.DuplicateEmailException;
import com.example.member.exception.MemberNotFoundException;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse create(MemberCreateRequest request){
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        } // true면 중복

        Member savedMember = new Member(
                request.getName(), request.getEmail()
        );

        memberRepository.save(savedMember);

        return MemberResponse.from(savedMember);

    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id){
        Member member = memberRepository.findById(id) // Optional
                .orElseThrow(()->new MemberNotFoundException(id));

        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll(){
        List<Member> members = memberRepository.findAll();

        return members
                .stream()
                .map(MemberResponse::from)
                .toList();

    }

    @Transactional
    public MemberResponse updateName(
            Long id,
            MemberUpdateRequest request
    ) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException(id)
                );

        member.changeName(request.getName());
        //dirty checking

        return MemberResponse.from(member);
    }

    @Transactional
    public void withdraw(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException(id)
                );

        member.withdraw();
    }

}
