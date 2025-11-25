package com.starwars.api.domain.Users.Dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    String username;
    String password;
}
