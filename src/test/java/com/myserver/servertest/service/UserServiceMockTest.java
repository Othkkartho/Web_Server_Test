package com.myserver.servertest.service;

import com.myserver.servertest.Service.user.RestUserService;
import com.myserver.servertest.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

@RunWith(SpringRunner.class)
@RestClientTest(RestUserService.class)
public class UserServiceMockTest {

    @Autowired
    private RestUserService userService;
    @Autowired
    private MockRestServiceServer server;

    @Test
    public void 회원가져오기() throws Exception {
        //given
        server
                .expect(MockRestRequestMatchers
                        .requestTo("/v1/user/email/dnstlr2933@naver.com"))
                .andRespond(MockRestResponseCreators
                        .withSuccess(
                                new ClassPathResource("/test.json", getClass()),
                                MediaType.APPLICATION_JSON)
                );

        //when
        User byEmail = userService.getUserByEmail("dnstlr2933@naver.com");

        //then
        Assertions.assertThat(byEmail.getEmail()).isEqualTo("dnstlr2933@naver.com");
        Assertions.assertThat(byEmail.getName()).isEqualTo("woonsik");
        server.verify();
    }
}