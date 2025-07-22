package org.example.repository.impl;

import org.example.config.HibernateUtil;
import org.example.model.Developer;
import org.example.model.Skill;
import org.example.model.Specialty;
import org.example.repository.DeveloperRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    @Override
    public List<Developer> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Developer", Developer.class).list();
        }
    }

    @Override
    public Developer getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Developer.class, id);
        }
    }

    @Override
    public void save(Developer developer) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // Каскадное сохранение автоматически сохранит навыки и специальности
            session.persist(developer);

            for (Skill skill : developer.getSkills()) {
                session.merge(skill); // Используем merge для существующих навыков
            }
            for (Specialty specialty : developer.getSpecialties()) {
                session.merge(specialty); // Используем merge для существующих специальностей
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error saving developer", e);
        }
    }

    @Override
    public void update(Developer developer) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(developer);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка обновления данных разработчика", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Developer dev = session.find(Developer.class, id);
            if (dev != null) {
                session.remove(dev);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка удаления данных разработчика", e);
        }
    }
}