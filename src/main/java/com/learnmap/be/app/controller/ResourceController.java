package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqResourceDto;
import com.learnmap.be.domain.dto.ResResourceDto;
import com.learnmap.be.domain.entities.Resource;
import com.learnmap.be.domain.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping("/{id}")
    public ResResourceDto getResourceById(@PathVariable("id") Long id) {
        return resourceService.getResourceById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Resource createResource(@Valid @RequestBody ReqResourceDto reqResourceDto) {
        return resourceService.createResource(reqResourceDto);
    }
}
