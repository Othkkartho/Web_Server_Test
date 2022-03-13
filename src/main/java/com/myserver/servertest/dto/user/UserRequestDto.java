package com.myserver.servertest.dto.user;

import com.myserver.servertest.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    private String email;
    private String name;
    private String nickname;

    @Builder
    public UserRequestDto(String email, String name, String nickname) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .nickName(nickname)
                .build();
    }
}