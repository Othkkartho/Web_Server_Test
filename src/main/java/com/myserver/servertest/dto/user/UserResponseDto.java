package com.myserver.servertest.dto.user;

import com.myserver.servertest.domain.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String email;
    private final String name;
    private final String nickname;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickName();
    }
}
