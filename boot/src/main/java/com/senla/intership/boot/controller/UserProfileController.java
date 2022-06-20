package com.senla.intership.boot.controller;

import com.senla.intership.boot.dto.post.PostDto;
import com.senla.intership.boot.dto.profile.UserProfileDto;
import com.senla.intership.boot.dto.profile.UserProfileWithAllDto;
import com.senla.intership.boot.service.UserProfileService;
import com.senla.intership.boot.util.SortHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
@Validated
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get profile by id", response = PostDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Profile is not exist")
    })
    public ResponseEntity<UserProfileWithAllDto> get(@PathVariable @NotNull(message = "Profiles id can not be null")
                                                     @Positive(message = "Profiles id can not be negative") Long id) {
        UserProfileWithAllDto response = userProfileService.read(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete profile by id", response = PostDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Profile is not exist")
    })
    @PreAuthorize("@userProfileServiceImpl.read(#id).user.username == authentication.name" + "|| hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @NotNull(message = "Profiles id can not be null")
                                       @Positive(message = "Profiles id can not be negative") Long id) {
        userProfileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "Update profile by id", response = PostDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Profile is not exist")
    })
    @PreAuthorize("@userProfileServiceImpl.read(#request.id).user.username == authentication.name" + "|| hasRole('ADMIN')")
    public ResponseEntity<UserProfileDto> update(@RequestBody UserProfileDto request) {
        UserProfileDto response = userProfileService.update(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation(value = "Create profile", response = PostDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Invalid data")
    })
    public ResponseEntity<UserProfileWithAllDto> create(@RequestBody UserProfileDto request) {
        UserProfileWithAllDto response = userProfileService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ApiOperation(value = "Get all profiles", response = PostDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Profiles are not exist")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserProfileDto>> getAll(@RequestParam(required = false, defaultValue = "id") String[] sort,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "asc") String direction,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        Sort allSorts = SortHelper.getAllSortValues(direction, sort);
        Pageable pageable = PageRequest.of(page, size, allSorts);
        Page<UserProfileDto> result = userProfileService.findAll(pageable);
        return ResponseEntity.ok(result.getContent());
    }

    @GetMapping("/friends")
    @ApiOperation(value = "Get friends", response = PostDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Friends are not exist")
    })
    public ResponseEntity<List<UserProfileDto>> getFriends(@RequestParam(required = false, defaultValue = "id") String[] sort,
                                                           @RequestParam(required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(required = false, defaultValue = "asc") String direction,
                                                           @RequestParam(required = false, defaultValue = "10") Integer size) {
        Sort allSorts = SortHelper.getAllSortValues(direction, sort);
        Pageable pageable = PageRequest.of(page, size, allSorts);
        Page<UserProfileDto> result = userProfileService.findFriends(pageable);
        return ResponseEntity.ok(result.getContent());
    }
}
