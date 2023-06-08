package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.service.group.GroupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/add")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPPORT')")
    public ResponseEntity<GroupEntity> addGroup(
            @RequestBody GroupCreateDto groupCreateDto
    ){
            return ResponseEntity.ok(groupService.save(groupCreateDto));
    }
}
