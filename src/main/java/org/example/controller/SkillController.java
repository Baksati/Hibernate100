package org.example.controller;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.repository.impl.SkillRepositoryImpl;

import java.util.List;

public class SkillController {

    private final SkillRepository skillRepository = new SkillRepositoryImpl();

    public List<Skill> getAllSkills() {
        return skillRepository.getAll();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.getById(id);
    }

    public void saveSkill(Skill skill) {
        skillRepository.save(skill);
    }

    public void updateSkill(Skill skill) {
        skillRepository.update(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.delete(id);
    }
}