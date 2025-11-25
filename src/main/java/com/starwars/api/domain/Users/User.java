package com.starwars.api.domain.Users;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User{
    @Id
    @Column(name = "id_user")
    Long id;

    @Column(name = "dni")
    Integer dni;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;
    @Column(name = "is_authorized")
    Boolean isAuthorized;
}
