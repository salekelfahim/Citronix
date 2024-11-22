package com.example.citronix.dto.mapper;

import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.domain.Tree;
import com.example.citronix.dto.HarvestDTO;
import com.example.citronix.dto.HarvestDetailDTO;
import com.example.citronix.repository.TreeRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HarvestMapper {
    private final TreeRepository treeRepository;

    public HarvestMapper(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    public Harvest toEntity(HarvestDTO dto) {
        if (dto == null) {
            return null;
        }

        Harvest harvest = new Harvest();
        harvest.setSeason(dto.getSeason());
        harvest.setHarvestDate(dto.getHarvestDate());
        harvest.setTotalQuantity(0.0);

        if (dto.getHarvestDetails() != null) {
            List<HarvestDetail> details = dto.getHarvestDetails().stream()
                    .map(detailDTO -> toHarvestDetail(detailDTO, harvest))
                    .collect(Collectors.toList());
            harvest.setHarvestDetails(details);
        }

        return harvest;
    }

    private HarvestDetail toHarvestDetail(HarvestDetailDTO dto, Harvest harvest) {
        HarvestDetail detail = new HarvestDetail();
        Tree tree = treeRepository.findById(dto.getTreeId())
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with id: " + dto.getTreeId()));

        detail.setQuantity(tree.getAnnualProductivity());
        detail.setHarvest(harvest);
        detail.setTree(tree);

        return detail;
    }

    public HarvestDTO toDto(Harvest entity) {
        if (entity == null) {
            return null;
        }

        HarvestDTO dto = new HarvestDTO();
        dto.setSeason(entity.getSeason());
        dto.setHarvestDate(entity.getHarvestDate());
        dto.setTotalQuantity(entity.getTotalQuantity());

        if (entity.getHarvestDetails() != null) {
            List<HarvestDetailDTO> detailDTOs = entity.getHarvestDetails().stream()
                    .map(this::toHarvestDetailDto)
                    .collect(Collectors.toList());
            dto.setHarvestDetails(detailDTOs);
        }

        return dto;
    }

    private HarvestDetailDTO toHarvestDetailDto(HarvestDetail detail) {
        HarvestDetailDTO dto = new HarvestDetailDTO();
        dto.setTreeId(detail.getTree().getId());
        return dto;
    }
}