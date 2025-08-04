package org.example.repository.impl;

import org.example.config.HibernateUtil;
import org.example.model.Developer;
import org.example.model.Skill;
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
            Developer developer = session.find(Developer.class, id);
            for (Skill skill : developer.getSkills()) {
                System.out.println(skill.getName());        }
            return developer;
        }
    }

    /* @Override
    public Developer getById(Long id) {
        Developer developer = new Developer();
        try (Session session = HibernateUtil.getSession()){
            developer =  (Developer) session.createQuery
                    ("SELECT d FROM Developer d JOIN FETCH d.skills JOIN FETCH d.specialty " +
                            "WHERE d.id = (:id)").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return developer;
    }
     */

    @Override
    public void save(Developer developer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(developer);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка сохранения разработчика", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Developer developer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(developer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Developer dev = session.find(Developer.class, id);
            if (dev != null) {
                session.remove(dev);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Ошибка при удалении разработчика", e);
        } finally {
            session.close();
        }
    }
}