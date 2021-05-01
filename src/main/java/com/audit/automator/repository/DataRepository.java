package com.audit.automator.repository;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Repository
@Slf4j
public class DataRepository{

    protected static final Logger logger = LoggerFactory.getLogger(DataRepository.class);

    private static EntityManagerFactory emf;

    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("automatorService");
        entityManager = emf.createEntityManager();
    }


    public Object create(Object entity) {
        log.debug("CREATING {}", entity);
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }


    public void createBulk(Collection<?> entities) {
        if (entities != null) {
            Iterator var2 = entities.iterator();

            while (var2.hasNext()) {
                Object entity = var2.next();
                this.create(entity);
            }

        }
    }

    public void delete(Object entity) {
        this.entityManager.remove(entity);
    }

    public void deleteBulk(Collection<?> entities) {
        if (entities != null) {
            Iterator var2 = entities.iterator();

            while (var2.hasNext()) {
                Object entity = var2.next();
                this.delete(entity);
            }

        }
    }

    public <T> T update(T entity) {
        return this.entityManager.merge(entity);
    }

    public void updateBulk(Collection<?> entities) {
        if (entities != null) {
            Iterator var2 = entities.iterator();

            while (var2.hasNext()) {
                Object entity = var2.next();
                this.update(entity);
            }

        }
    }

    public <T> T findById(Class<T> entityClass, Object primaryKey) {
        return this.entityManager.find(entityClass, primaryKey);
    }

    public <T> T findById(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
        return this.entityManager.find(entityClass, primaryKey, lockMode);
    }

    public <T> T findById(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
        return this.entityManager.find(entityClass, primaryKey, properties);
    }


}
