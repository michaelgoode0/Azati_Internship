package com.azati.microservice.controller;

import com.azati.microservice.dto.LoginDto;
import com.azati.microservice.dto.UserDto;
import com.azati.microservice.model.RoleName;
import com.azati.microservice.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/token")
    public String createToken(@RequestBody LoginDto dto) {
        return loginService.signIn(dto);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registration(@RequestBody LoginDto dto) {
        return ResponseEntity.ok(loginService.signUp(dto));
    }
}
