package com.myserver.servertest.service;

import com.myserver.servertest.Service.user.KakaoService;
import com.myserver.servertest.dto.social.KakaoProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "mockUser")
public class KakaoServiceTest {

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    Environment env;

    private static String accessToken;

    @Before
    public void setToken() {
        accessToken = env.getProperty("social.kakao.accessToken");
    }

    @Test
    public void 액세스토큰으로_사용자정보_요청() throws Exception
    {
        //given
        //when
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);

        //then
        assertThat(kakaoProfile).isNotNull();
        assertThat(kakaoProfile.getKakao_account().getEmail()).isEqualTo("email1@gmail.com");
        assertThat(kakaoProfile.getProperties().getNickname()).isEqualTo("nickname1");
    }
}