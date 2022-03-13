package com.myserver.servertest.dto.sign;

import com.myserver.servertest.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserLoginResponseDto {
    private final Long userId;
    private final List<String> roles;
    private final LocalDateTime createdDate;

    public UserLoginResponseDto(User user) {
        this.userId = user.getUserId();
        this.roles = user.getRoles();
        this.createdDate = user.getCreatedDate();
    }
}