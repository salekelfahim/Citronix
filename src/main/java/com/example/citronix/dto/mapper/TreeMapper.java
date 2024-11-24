package com.example.citronix.dto.mapper;

import com.example.citronix.domain.Tree;
import com.example.citronix.dto.TreeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    @Mapping(source = "field.id", target = "fieldId")
    TreeDTO toDTO(Tree tree);

    @Mapping(source = "fieldId", target = "field.id")
    Tree toEntity(TreeDTO treeDTO);
}
