package org.example.repository.impl;

import org.example.config.HibernateUtil;
import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {
    private static final Logger logger = LoggerFactory.getLogger(SkillRepositoryImpl.class);

    @Override
    public List<Skill> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Skill", Skill.class).list();
        } catch (Exception e) {
            logger.error("Ошибка при получении списка навыков", e);
            throw new RuntimeException("Не удалось получить список навыков", e);
        }
    }

    @Override
    public Skill getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Skill.class, id);
        } catch (Exception e) {
            logger.error("Ошибка при поиске навыка по ID: {}", id, e);
            throw new RuntimeException("Не удалось найти навык", e);
        }
    }

    @Override
    public void save(Skill skill) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(skill);
            transaction.commit();
            logger.info("Навык сохранен: {}", skill.getId());
        } catch (Exception e) {
            handleTransactionException(transaction, "Ошибка сохранения навыка", e);
            throw new RuntimeException("Не удалось сохранить навык", e);
        }
    }

    @Override
    public void update(Skill skill) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(skill);
            transaction.commit();
            logger.info("Навык обновлен: {}", skill.getId());
        } catch (Exception e) {
            handleTransactionException(transaction, "Ошибка обновления навыка", e);
            throw new RuntimeException("Не удалось обновить навык", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Skill skill = session.find(Skill.class, id);
            if (skill != null) {
                session.remove(skill);
                logger.info("Навык удален ID: {}", id);
            }
            transaction.commit();
        } catch (Exception e) {
            handleTransactionException(transaction, "Ошибка удаления навыка", e);
            throw new RuntimeException("Не удалось удалить навык", e);
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

