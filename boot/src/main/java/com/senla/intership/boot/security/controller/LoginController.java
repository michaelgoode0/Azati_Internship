package com.senla.intership.boot.security.controller;

import com.senla.intership.boot.api.service.UserService;
import com.senla.intership.boot.dto.user.LoginDto;
import com.senla.intership.boot.dto.user.UserDto;
import com.senla.intership.boot.dto.user.UserWithAllDto;
import com.senla.intership.boot.dto.user.UserWithRolesDto;
import com.senla.intership.boot.security.enums.RoleName;
import com.senla.intership.boot.util.AuthNameHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController{

    private final UserService userService;


    @PostMapping("/signin")
    public String getToken(@RequestBody LoginDto request) {
        return userService.signIn(request);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserWithRolesDto> signUp(@RequestBody LoginDto request) {
        UserWithRolesDto response = userService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public  ResponseEntity<String> getUsername(){
        return  ResponseEntity.ok(AuthNameHolder.getAuthUsername());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
