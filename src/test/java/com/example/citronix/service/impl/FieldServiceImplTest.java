package com.example.citronix.service.impl;

import com.example.citronix.domain.Farm;
import com.example.citronix.domain.Field;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.repository.FieldRepository;
import com.example.citronix.web.errors.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class FieldServiceImplTest {

    @InjectMocks
    private FieldServiceImpl fieldService;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ValidField_ShouldSaveSuccessfully() {
        Farm farm = new Farm();
        farm.setId(1L);
        farm.setArea(10.0);

        Field field = new Field();
        field.setArea(2.0);
        field.setFarm(farm);

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(fieldRepository.findByFarmId(1L)).thenReturn(List.of());
        when(fieldRepository.save(field)).thenReturn(field);

        Field result = fieldService.save(field);

        assertThat(result).isNotNull();
        verify(fieldRepository, times(1)).save(field);
    }

    @Test
    void save_FieldAreaExceedsFarmArea_ShouldThrowException() {
        Farm farm = new Farm();
        farm.setId(1L);
        farm.setArea(10.0);

        Field field = new Field();
        field.setArea(6.0);
        field.setFarm(farm);

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(fieldRepository.findByFarmId(1L)).thenReturn(List.of());

        assertThrows(ExceededFieldAreaException.class, () -> fieldService.save(field));
        verify(fieldRepository, never()).save(field);
    }

    @Test
    void update_ValidField_ShouldUpdateSuccessfully() {
        Farm farm = new Farm();
        farm.setId(1L);
        farm.setArea(10.0);

        Field existingField = new Field();
        existingField.setId(1L);
        existingField.setArea(3.0);
        existingField.setFarm(farm);

        Field updatedField = new Field();
        updatedField.setArea(4.0);

        when(fieldRepository.findById(1L)).thenReturn(Optional.of(existingField));
        when(fieldRepository.findByFarmId(1L)).thenReturn(List.of(existingField));
        when(fieldRepository.save(existingField)).thenReturn(existingField);

        Field result = fieldService.update(1L, updatedField);

        assertThat(result.getArea()).isEqualTo(4.0);
        verify(fieldRepository, times(1)).save(existingField);
    }

    @Test
    void delete_NonExistingField_ShouldThrowException() {
        when(fieldRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> fieldService.delete(1L));
        verify(fieldRepository, never()).deleteById(1L);
    }

    @Test
    void findByFarmId_ValidFarm_ShouldReturnFields() {
        Farm farm = new Farm();
        farm.setId(1L);

        Field field1 = new Field();
        field1.setId(1L);
        field1.setArea(2.0);

        Field field2 = new Field();
        field2.setId(2L);
        field2.setArea(3.0);

        when(farmRepository.existsById(1L)).thenReturn(true);
        when(fieldRepository.findByFarmId(1L)).thenReturn(List.of(field1, field2));

        List<Field> fields = fieldService.findByFarmId(1L);

        assertThat(fields).hasSize(2);
        verify(fieldRepository, times(1)).findByFarmId(1L);
    }

    @Test
    void save_FarmDoesNotExist_ShouldThrowException() {
        Field field = new Field();
        field.setFarm(new Farm());
        field.getFarm().setId(1L);

        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FarmNotFoundException.class, () -> fieldService.save(field));
        verify(fieldRepository, never()).save(field);
    }




}
