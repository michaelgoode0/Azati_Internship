package com.senla.intership.boot.controller;

import com.senla.intership.boot.dto.comment.PostCommentWithAllDto;
import com.senla.intership.boot.dto.comment.PostCommentWithPostDto;
import com.senla.intership.boot.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping
    public ResponseEntity<PostCommentWithPostDto> create(@RequestBody @Valid PostCommentWithPostDto request) {
        PostCommentWithPostDto response = postCommentService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<PostCommentWithPostDto> update(@RequestBody @Valid PostCommentWithPostDto request) {
        PostCommentWithPostDto response = postCommentService.update(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCommentWithAllDto> read(@PathVariable @NotNull(message = "Post comment id can not be null")
                                                      @Positive(message = "Post comment id can not be negative") Long id) {
        PostCommentWithAllDto response = postCommentService.read(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull(message = "Post comment id can not be null")
                                       @Positive(message = "Post comment id can not be negative") Long id) {
        postCommentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
