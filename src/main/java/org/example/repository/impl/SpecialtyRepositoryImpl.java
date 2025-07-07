package org.example.repository.impl;

import org.example.config.HibernateUtil;
import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    @Override
    public List<Specialty> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Specialty", Specialty.class).list();
        }
    }

    @Override
    public Specialty getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Specialty.class, id);
        }
    }

    @Override
    public void save(Specialty specialty) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(specialty);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка сохранения данных специальности", e);
        }
    }

    @Override
    public void update(Specialty specialty) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(specialty);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка обновления данных специальности", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Specialty specialty = session.find(Specialty.class, id);
            if (specialty != null) {
                session.remove(specialty);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка удаления данных специальности", e);
        }
    }
}
