package com.myserver.servertest.dto.sign;

import com.myserver.servertest.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String nickName;

    @Builder
    public UserSignupRequestDto(String email, String password, String name, String nickName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickName(nickName)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}