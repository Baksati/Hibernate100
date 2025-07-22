package org.example.repository.impl;

import org.example.config.HibernateUtil;
import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {

    @Override
    public List<Skill> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Skill", Skill.class).list();
        }
    }

    @Override
    public Skill getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Skill.class, id);
        }
    }

    @Override
    public void save(Skill skill) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(skill);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка сохранения данных навыка", e);
        }
    }

    @Override
    public void update(Skill skill) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(skill);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка обновления данных навыка", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Skill skill = session.find(Skill.class, id);
            if (skill != null) {
                session.remove(skill);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка удаления данных навыка", e);
        }
    }
}