package com.senla.intership.boot.controller;

import com.senla.intership.boot.dto.hashtag.HashtagWithPostsDto;
import com.senla.intership.boot.service.HashtagService;
import com.senla.intership.boot.util.SortHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hashtags")
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    @ApiOperation(value = "Get all hashtags")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Hashtags are not exist.")
    })
    public ResponseEntity<List<HashtagWithPostsDto>> getAll(@RequestParam(required = false, defaultValue = "id") String sort,
                                                            @RequestParam(required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(required = false, defaultValue = "asc") String direction,
                                                            @RequestParam(required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortHelper.orderDirection(direction), sort));
        Page<HashtagWithPostsDto> result = hashtagService.getAll(pageable);
        return ResponseEntity.ok(result.getContent());
    }

    @GetMapping("/top")
    @ApiOperation(value = "Get top hashtags")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 403, message = "Hashtags are not exist.")
    })
    public ResponseEntity<List<HashtagWithPostsDto>> findTopHashtags(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                     @RequestParam(required = false, defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<HashtagWithPostsDto> result = hashtagService.getAllTop(pageable);
        return ResponseEntity.ok(result.getContent());
    }
}
