package com.senla.intership.boot.security.controller;

import com.senla.intership.boot.dto.user.LoginDto;
import com.senla.intership.boot.dto.user.UserWithRolesDto;
import com.senla.intership.boot.service.UserService;
import com.senla.intership.boot.util.AuthNameHolder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;


    @PostMapping("/signin")
    @ApiOperation(value = "Sign in", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Invalid Data")
    })
    public String getToken(@RequestBody LoginDto request) {
        return userService.signIn(request);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "Sign up", response = UserWithRolesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Invalid Data")
    })
    public ResponseEntity<UserWithRolesDto> signUp(@RequestBody LoginDto request) {
        UserWithRolesDto response = userService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    @ApiOperation(value = "Get username", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "User is not exist")
    })
    public ResponseEntity<String> getUsername() {
        return ResponseEntity.ok(AuthNameHolder.getAuthUsername());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete user", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "User is not exist")
    })
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
