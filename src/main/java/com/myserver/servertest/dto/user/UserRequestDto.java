package com.myserver.servertest.dto.user;

import com.myserver.servertest.domain.user.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String email;
    private String name;
    private String nickname;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .nickName(nickname)
                .build();
    }
}