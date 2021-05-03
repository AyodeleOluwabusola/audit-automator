package com.audit.automator.repository;

import com.audit.automator.entities.Configuration;
import com.audit.automator.enums.ConfigEnum;
import com.audit.automator.utils.ProxyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class DataRepository{

    private static EntityManagerFactory emf;

    private EntityManager entityManager;

    @Autowired
    private ProxyUtil proxyUtil;

    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("automatorService");
        entityManager = emf.createEntityManager();
    }




    public String
    getConfigValue(ConfigEnum configEnum) throws PersistenceException, IllegalStateException {
        return getConfigValue(configEnum.getName(), configEnum.getValue(), configEnum.getDescription());
    }


    public String getConfigValue(String instanceSettingName, String defaultValue, String description) throws PersistenceException, SecurityException, IllegalStateException {

        String name = instanceSettingName;

        List<String> values = null;
        try {

            Query query = entityManager.createQuery("select c.value from Configuration c where c.name = :name");

            query.setParameter("name", name);

            values = query.getResultList();

        } catch (PersistenceException | SecurityException | IllegalStateException e) {
            log.error("Exception getting Settings value ", e);
        }

        String configValue = null;

        if(values != null && !values.isEmpty()) {

            if(values.get(0) == null) {
                configValue = "";
            } else {
                configValue = values.get(0);
            }
        }

        String val;
        if (configValue != null) {
            val = configValue.trim();
        } else {
            val = defaultValue;
            log.debug("Retrieving {} setting with value {}", name, val);

            Configuration configuration = new Configuration();

            configuration.setName(name);
            configuration.setValue(StringUtils.EMPTY.equals(defaultValue) ? " " : defaultValue); // oracle sees empty string as null and throws an exception since value field is not nullable
            configuration.setDescription(description);

            try {

                final Configuration localConfiguration = configuration;

                proxyUtil.executeWithNewTransaction(() -> create(localConfiguration));
            } catch (PersistenceException | SecurityException | IllegalStateException e) {
                log.error("Exception getting Settings value ", e);
            }
        }

        return val;
    }


    public Object create(Object entity) {
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
