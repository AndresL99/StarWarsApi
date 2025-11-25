package com.starwars.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.api.domain.Users.User;
import com.starwars.api.domain.Users.Dto.LoginRequestDto;
import com.starwars.api.domain.Users.Dto.LoginResponseDto;
import com.starwars.api.domain.Users.Dto.UserDto;
import com.starwars.api.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.starwars.api.utils.Constants.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    private UserService userService;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    
    @Autowired
    public LoginController(UserService userService, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<User>saveUser(@RequestBody User source) throws Exception
    {
        User user = userService.saveUser(source);
        return ResponseEntity.ok(user);
    }


    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto loginRequestDto)
    {
        log.info(loginRequestDto.toString());
        User user = userService.getByUsernameAndPassword(loginRequestDto.getUsername(),loginRequestDto.getPassword());
        if(user!=null)
        {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setToken(this.generateToken(userDto));
            return ResponseEntity.ok(loginResponseDto);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String generateToken(UserDto user)
    {
        try {
            String authority = user.getIsAuthorized() ? AUTHORIZED : NOT_AUTHORIZED;
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(user.getUsername())
                    .claim("user", objectMapper.writeValueAsString(user))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }
    }



}
