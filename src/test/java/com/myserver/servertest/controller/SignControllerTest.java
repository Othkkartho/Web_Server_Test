package com.myserver.servertest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myserver.servertest.domain.user.User;
import com.myserver.servertest.domain.user.UserJpaRepo;
import com.myserver.servertest.dto.sign.UserLoginRequestDto;
import com.myserver.servertest.dto.sign.UserSignupRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        userJpaRepo.save(User.builder()
                .name("name1")
                .password(passwordEncoder.encode("password1"))
                .nickName("nickname1")
                .email("email1@gmail.com")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

    @Test
    public void 로그인_성공() throws Exception {
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("email1@gmail.com")
                .password("password1")
                .build());
        //given
        ResultActions actions = mockMvc.perform(post("/v1/sign/login")
                .content(object)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();

        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .email("email1@gmail.com" + time)
                .nickName("nickname1")
                .name("name1")
                .password("password1")
                .build());
        ResultActions actions = mockMvc.perform(
                post("/v1/sign/signup")
                        .content(object)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions.
                andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }

    @Test
    public void 회원가입_실패() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UserSignupRequestDto.builder()
                .name("name1")
                .email("email1@gmail.com")
                .password("password1")
                .nickName("nickname1")
                .build());

        //when
        ResultActions actions = mockMvc.perform(post("/v1/sign/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(object));

        //then
        actions.andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-1002));
    }

    @Test
    public void 로그인_실패() throws Exception
    {
        //given
        String object = objectMapper.writeValueAsString(UserLoginRequestDto.builder()
                .email("email1@gmail.com")
                .password("wrongPassword")
                .build());
        //when
        ResultActions actions = mockMvc.perform(post("/v1/sign/login")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-1001));
    }
}