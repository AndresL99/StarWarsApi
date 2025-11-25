package com.starwars.api.domain.Users.Dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    String token;
}
