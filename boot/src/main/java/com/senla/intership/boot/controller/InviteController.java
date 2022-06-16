package com.senla.intership.boot.controller;

import com.senla.intership.boot.dto.invite.InviteDto;
import com.senla.intership.boot.service.InviteService;
import com.senla.intership.boot.util.SortHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invites")
public class InviteController {

    private final InviteService inviteService;

    @GetMapping("/{id}")
    public ResponseEntity<InviteDto> sendInvite(@PathVariable Long id) {
        InviteDto response = inviteService.sendInvite(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/accept")
    @PreAuthorize("@inviteServiceImpl.read(#id).userTo.user.username==authentication.name" + "|| hasRole('ADMIN')")
    public ResponseEntity<InviteDto> acceptInvite(@PathVariable Long id) {
        InviteDto response = inviteService.acceptInvite(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/deny")
    @PreAuthorize("@inviteServiceImpl.read(#id).userTo.user.username==authentication.name" + "|| hasRole('ADMIN')")
    public ResponseEntity<InviteDto> denyInvite(@PathVariable Long id) {
        InviteDto response = inviteService.denyInvite(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InviteDto>> getAll(@RequestParam(required = false, defaultValue = "id") String sort,
                                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "asc") String direction,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortHelper.orderDirection(direction), sort));
        Page<InviteDto> result = inviteService.findAll(pageable);
        return ResponseEntity.ok(result.getContent());
    }

}
