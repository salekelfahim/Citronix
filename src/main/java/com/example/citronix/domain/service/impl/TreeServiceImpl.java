package com.example.citronix.domain.service.impl;

import com.example.citronix.domain.Field;
import com.example.citronix.domain.Tree;
import com.example.citronix.domain.service.FieldService;
import com.example.citronix.domain.service.TreeService;
import com.example.citronix.repository.TreeRepository;
import com.example.citronix.web.errors.ExceededTreeDensityException;
import com.example.citronix.web.errors.InvalidPlantingPeriodException;
import com.example.citronix.web.errors.NonProductiveTreeException;
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
}
