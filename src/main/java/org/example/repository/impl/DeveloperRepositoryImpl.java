package org.example.repository.impl;

import org.example.config.HibernateUtil;
import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final Logger logger = LoggerFactory.getLogger(DeveloperRepositoryImpl.class);

    @Override
    public List<Developer> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Developer> developers = session.createQuery("FROM Developer", Developer.class).list();
            logger.debug("Получено {} разработчиков", developers.size());
            return developers;
        } catch (Exception e) {
            logger.error("Ошибка при получении списка разработчиков", e);
            throw new RuntimeException("Не удалось получить список разработчиков", e);
        }
    }

    @Override
    public Developer getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Developer developer = session.find(Developer.class, id);
            if (developer != null) {
                logger.debug("Найден разработчик ID: {}", id);
            } else {
                logger.warn("Разработчик с ID {} не найден", id);
            }
            return developer;
        } catch (Exception e) {
            logger.error("Ошибка при поиске разработчика по ID: {}", id, e);
            throw new RuntimeException("Не удалось найти разработчика", e);
        }
    }

    @Override
    public void save(Developer developer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(developer);
            transaction.commit();
            logger.info("Разработчик успешно сохранен: {}", developer.getId());
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                    logger.debug("Транзакция откачена");
                } catch (Exception rollbackEx) {
                    logger.error("Ошибка при откате транзакции", rollbackEx);
                }
            }
            logger.error("Ошибка сохранения разработчика: {}", developer, e);
            throw new RuntimeException("Не удалось сохранить разработчика", e);
        }
    }

    @Override
    public void update(Developer developer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(developer);
            transaction.commit();
            logger.info("Разработчик обновлен: {}", developer.getId());
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    logger.error("Ошибка при откате транзакции", rollbackEx);
                }
            }
            logger.error("Ошибка обновления разработчика ID: {}", developer.getId(), e);
            throw new RuntimeException("Не удалось обновить разработчика", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Developer developer = session.find(Developer.class, id);
            if (developer != null) {
                session.remove(developer);
                logger.info("Разработчик удален ID: {}", id);
            } else {
                logger.warn("Попытка удаления несуществующего разработчика ID: {}", id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    logger.error("Ошибка при откате транзакции", rollbackEx);
                }
            }
            logger.error("Ошибка удаления разработчика ID: {}", id, e);
            throw new RuntimeException("Не удалось удалить разработчика", e);
        }
    }
}
