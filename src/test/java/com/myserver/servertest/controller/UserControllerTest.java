package com.myserver.servertest.controller;

import com.myserver.servertest.Service.user.UserService;
import com.myserver.servertest.domain.user.User;
import com.myserver.servertest.domain.user.UserJpaRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "mockUser")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserJpaRepo userJpaRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static int id;

    @Before
    public void setUp() {
        User save = userJpaRepo.save(User.builder()
                .name("name1")
                .password(passwordEncoder.encode("password1"))
                .nickName("nickname1")
                .email("email1@gmail.com")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        id = Math.toIntExact(save.getUserId());
    }

    @Test
    public void ????????????_?????????() throws Exception {
        //then
        ResultActions actions = mockMvc.perform(
                get("/v1/user/email/{email}", "email1@gmail.com")
                        .param("lang", "ko"));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email", is("email1@gmail.com")))
                .andExpect(jsonPath("$.data.name", is("name1")))
                .andReturn();
    }

    @Test
    public void ????????????_userId() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(get("/v1/user/id/{id}", id)
                .param("lang", "ko"));
        //when
        //then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId", is(id)))
                .andExpect(jsonPath("$.data.email", is("email1@gmail.com")))
                .andExpect(jsonPath("$.data.name", is("name1")));
    }

    @Test
    public void ??????_????????????() throws Exception {
        //then
        mockMvc.perform(get("/v1/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void ????????????() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userId", String.valueOf(id));
        params.add("nickName", "afterNickName");
        //when
        ResultActions actions = mockMvc.perform(put("/v1/user")
                .params(params));
        //then
        actions
                .andDo(print())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data", is(id)));
    }

    @Test
    public void ????????????() throws Exception {
        //given
        //when
        ResultActions actions = mockMvc.perform(delete("/v1/user/{id}", id));
        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.msg").exists());
    }
}