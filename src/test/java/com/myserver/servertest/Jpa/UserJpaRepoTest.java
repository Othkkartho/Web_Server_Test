package com.myserver.servertest.Jpa;

import com.myserver.servertest.advice.exception.CUserNotFoundException;
import com.myserver.servertest.domain.user.User;
import com.myserver.servertest.domain.user.UserJpaRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserJpaRepoTest {

    @Autowired
    private UserJpaRepo userJpaRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String name = "name1";
    private String email = "email1@gmail.com";
    private String password = "password1";


    @Test
    public void 회원저장_후_이메일로_회원검색() throws Exception {

        //given
        userJpaRepo.save(User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickName(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        //when
        User user = userJpaRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        //then
        assertNotNull(user);
        assertEquals(user.getUsername(), user.getUsername());
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getNickName()).isEqualTo(name);
    }
}
