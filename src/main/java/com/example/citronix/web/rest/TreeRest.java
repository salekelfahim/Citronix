package com.example.citronix.web.rest;

import com.example.citronix.domain.Tree;
import com.example.citronix.domain.service.TreeService;
import com.example.citronix.domain.service.dto.TreeDTO;
import com.example.citronix.domain.service.dto.mapper.TreeMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trees")
public class TreeRest {

    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeRest(TreeService treeService, TreeMapper treeMapper) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<TreeDTO> save(@RequestBody TreeDTO treeDTO) {
        Tree tree = treeMapper.toEntity(treeDTO);

        treeService.validateTreeSpacing(tree.getField(), List.of(tree));

        Tree savedTree = treeService.save(tree);

        return ResponseEntity.ok(treeMapper.toDTO(savedTree));
    }
}
