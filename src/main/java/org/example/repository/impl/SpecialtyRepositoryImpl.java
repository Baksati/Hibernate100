package org.example.repository.impl;

import org.example.config.HibernateUtil;
import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    private static final Logger logger = LoggerFactory.getLogger(SpecialtyRepositoryImpl.class);

    @Override
    public List<Specialty> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Specialty", Specialty.class).list();
        } catch (Exception e) {
            logger.error("Ошибка при получении списка специализаций", e);
            throw new RuntimeException("Не удалось получить список специализаций", e);
        }
    }

    @Override
    public Specialty getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Specialty.class, id);
        } catch (Exception e) {
            logger.error("Ошибка при поиске специализации по ID: {}", id, e);
            throw new RuntimeException("Не удалось найти специализацию", e);
        }
    }

    @Override
    public void save(Specialty specialty) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(specialty);
            transaction.commit();
            logger.info("Специализация сохранена: {}", specialty.getId());
        } catch (Exception e) {
            handleTransactionException(transaction, "Ошибка сохранения специализации", e);
            throw new RuntimeException("Не удалось сохранить специализацию", e);
        }
    }

    @Override
    public void update(Specialty specialty) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(specialty);
            transaction.commit();
            logger.info("Специализация обновлена: {}", specialty.getId());
        } catch (Exception e) {
            handleTransactionException(transaction, "Ошибка обновления специализации", e);
            throw new RuntimeException("Не удалось обновить специализацию", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Specialty specialty = session.find(Specialty.class, id);
            if (specialty != null) {
                session.remove(specialty);
                logger.info("Специализация удалена ID: {}", id);
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, "Ошибка удаления специализации", e);
            throw new RuntimeException("Не удалось удалить специализацию", e);
        }
    }

    private void handleTransactionException(Transaction transaction, String message, Exception e) {
        if (transaction != null && transaction.isActive()) {
            try {
                transaction.rollback();
            } catch (Exception rollbackEx) {
                logger.error("Ошибка при откате транзакции", rollbackEx);
            }
        }
        logger.error(message, e);
    }
}
