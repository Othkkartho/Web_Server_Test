package com.myserver.servertest.Service.user;

import com.myserver.servertest.domain.user.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestUserService {

    private final RestTemplate restTemplate;

    public RestUserService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public User getUserByEmail(String email) {
        return restTemplate.getForObject("/v1/user/email/{email}", User.class, email);
    }
}
