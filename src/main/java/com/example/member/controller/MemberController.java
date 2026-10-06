package com.example.member.controller;

import com.example.member.dto.request.MemberCreateRequest;
import com.example.member.dto.request.MemberUpdateRequest;
import com.example.member.dto.response.MemberResponse;
import com.example.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberResponse create(@Valid  @RequestBody MemberCreateRequest request){
        return memberService.create(request);
    }

    @GetMapping("/{id}")
    public MemberResponse findById(@PathVariable Long id){
        return memberService.findById(id);
    }


    @GetMapping
    public List<MemberResponse> findAll(){
        return memberService.findAll();
    }

    @PatchMapping("/{id}")
    public MemberResponse update(@Valid @PathVariable Long id, @RequestBody MemberUpdateRequest request){
        return memberService.updateName(id, request);
    }

    @DeleteMapping("/{id}")
    public void withdraw(@PathVariable Long id){
        memberService.withdraw(id);
    }



}
