package com.example.member.controller;

import com.example.member.domain.MemberStatus;
import com.example.member.dto.response.MemberResponse;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc; //mockMvc가 가짜http 요청

    @MockitoBean
    MemberService memberService; //mock

    @Test
    void findMemberSuccess() throws Exception {

        MemberResponse response = new MemberResponse(
                1L,
                "Kim",
                "kim@test.com",
                MemberStatus.ACTIVE,
                LocalDateTime.now()
        );

        given(memberService.findById(1L))
                .willReturn(response);



        mockMvc.perform(get("/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kim"))
                .andExpect(jsonPath("$.email").value("kim@test.com"));
    }


    @Test
    void createMemberSuccess() throws Exception {

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "Kim",
                        "email": "kim@test.com"
                    }
                    """))
                .andExpect(status().isOk());
    }

    @Test
    void createMemberFailWhenNameIsBlank() throws Exception {

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "",
                        "email": "kim@test.com"
                    }
                    """))
                .andExpect(status().isBadRequest());
    }
}