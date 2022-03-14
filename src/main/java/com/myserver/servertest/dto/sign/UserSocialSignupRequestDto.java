package com.myserver.servertest.dto.sign;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialSignupRequestDto {
    private String accessToken;
}
