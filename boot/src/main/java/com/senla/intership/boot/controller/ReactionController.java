package com.senla.intership.boot.controller;

import com.senla.intership.boot.dto.reaction.ReactionWithProfileDto;
import com.senla.intership.boot.service.ReactionService;
import com.senla.intership.boot.util.SortHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<ReactionWithProfileDto>> getAllByPost(@RequestParam(required = false, defaultValue = "id") String[] sort,
                                                                     @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                     @RequestParam(required = false, defaultValue = "asc") String direction,
                                                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                     @PathVariable Long postId) {
        Sort allSorts = SortHelper.getAllSortValues(direction, sort);
        Pageable pageable = PageRequest.of(page, size, allSorts);
        Page<ReactionWithProfileDto> result = reactionService.findAllByPost(pageable, postId);
        return ResponseEntity.ok(result.getContent());
    }
}
