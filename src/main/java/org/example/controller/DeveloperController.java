package org.example.controller;

import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.example.repository.impl.DeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {

    private final DeveloperRepository developerRepository;

    public DeveloperController() {
        this.developerRepository = new DeveloperRepositoryImpl();
    }

    public DeveloperController(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.getAll();
    }

    public Developer getDeveloperById(Long id) {
        return developerRepository.getById(id);
    }

    public void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
    }

    public void updateDeveloper(Developer developer) {
        developerRepository.update(developer);
    }

    public void deleteDeveloper(Long id) {
        developerRepository.delete(id);
    }
}