package org.example.controller;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.repository.impl.SpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {

    private final SpecialtyRepository specialtyRepository = new SpecialtyRepositoryImpl();

    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.getAll();
    }

    public Specialty getSpecialtyById(Long id) {
        return specialtyRepository.getById(id);
    }

    public void saveSpecialty(Specialty specialty) {
        specialtyRepository.save(specialty);
    }

    public void updateSpecialty(Specialty specialty) {
        specialtyRepository.update(specialty);
    }

    public void deleteSpecialty(Long id) {
        specialtyRepository.delete(id);
    }
}