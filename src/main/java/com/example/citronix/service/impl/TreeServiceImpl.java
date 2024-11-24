package com.example.citronix.service.impl;

import com.example.citronix.domain.Field;
import com.example.citronix.domain.Tree;
import com.example.citronix.service.FieldService;
import com.example.citronix.service.TreeService;
import com.example.citronix.repository.TreeRepository;
import com.example.citronix.web.errors.ExceededTreeDensityException;
import com.example.citronix.web.errors.InvalidPlantingPeriodException;
import com.example.citronix.web.errors.NonProductiveTreeException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final FieldService fieldService;

    public TreeServiceImpl(TreeRepository treeRepository, FieldService fieldService) {
        this.treeRepository = treeRepository;
        this.fieldService = fieldService;
    }

    @Override
    public Tree save(Tree tree) {
        if (!tree.isValidPlantingPeriod()) {
            throw new InvalidPlantingPeriodException();
        }

        if (!tree.isProductive()) {
            throw new NonProductiveTreeException();
        }

        return treeRepository.save(tree);
    }

    @Override
    public void validateTreeSpacing(Field field, List<Tree> trees) {
        double fieldArea = fieldService.findById(field.getId()).getArea();

        int maxAllowedTrees = (int) (fieldArea * 100);

        int existingTreeCount = treeRepository.countByFieldId(field.getId());

        if (existingTreeCount + trees.size() > maxAllowedTrees) {
            throw new ExceededTreeDensityException();
        }
    }

    @Override
    public Tree update(Long id, Tree updatedTree) {
        Tree existingTree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found with id: " + id));

        existingTree.setPlantingDate(updatedTree.getPlantingDate());

        if (updatedTree.getField() != null) {
            existingTree.setField(fieldService.findById(updatedTree.getField().getId()));
        }

        if (!existingTree.isValidPlantingPeriod()) {
            throw new InvalidPlantingPeriodException();
        }

        if (!existingTree.isProductive()) {
            throw new NonProductiveTreeException();
        }

        return treeRepository.save(existingTree);
    }

    @Override
    public void delete(Long id) {
        if (!treeRepository.existsById(id)) {
            throw new EntityNotFoundException("Tree not found with id: " + id);
        }

        treeRepository.deleteById(id);
    }
}
