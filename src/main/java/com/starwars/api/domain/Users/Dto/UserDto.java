package com.starwars.api.domain.Users.Dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    String username;
    Boolean isAuthorized;
}
