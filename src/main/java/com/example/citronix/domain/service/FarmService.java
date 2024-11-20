package com.example.citronix.domain.service;


import com.example.citronix.domain.service.dto.FarmDTO;
import org.springframework.stereotype.Service;

@Service
public interface FarmService {
    FarmDTO save(FarmDTO farmDTO);
    FarmDTO update(Long id, FarmDTO farmDTO);

}
