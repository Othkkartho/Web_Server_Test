package com.myserver.servertest.controller;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class KOAuthControllerTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;

    private String baseUrl;
    private String kakaoClientId;
    private String kakaoRedirectUri;

    @Before
    public void setUri() {
        baseUrl = env.getProperty("spring.url.base");
        kakaoClientId = env.getProperty("social.kakao.client-id");
        kakaoRedirectUri = env.getProperty("social.kakao.redirect");
    }

    @Test
    public void REST_API_KEY_카카오로그인_요청() {
        String loginUri = env.getProperty("social.kakao.url.login");

        ResponseEntity<String> forEntity
                = restTemplate.getForEntity(
                        loginUri + "/{response_type}" + "/{client_id}" + "/{redirect_uri}",
                        String.class,
                        "code", kakaoClientId, baseUrl + kakaoRedirectUri);

        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}