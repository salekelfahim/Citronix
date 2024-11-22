package com.example.citronix.service;

import com.example.citronix.domain.Field;
import com.example.citronix.domain.Tree;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TreeService {
    Tree save(Tree tree);
    void validateTreeSpacing(Field field, List<Tree> trees);
    Tree update(Long id, Tree updatedTree);
    void delete(Long id);
}
