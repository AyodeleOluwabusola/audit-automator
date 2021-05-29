package com.audit.automator.repository;

import com.audit.automator.entities.Configuration;
import com.audit.automator.entities.DocuSignLog;
import com.audit.automator.entities.DocuSignLog_;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class DataRepository{

    private static EntityManagerFactory emf;

    private EntityManager entityManager;
    protected CriteriaBuilder criteriaBuilder;


    @Autowired
    private ProxyUtil proxyUtil;

    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("automatorService");
        entityManager = emf.createEntityManager();
        criteriaBuilder = emf.getCriteriaBuilder();
    }




    public String getConfigValue(ConfigEnum configEnum) throws PersistenceException, IllegalStateException {
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

    public Integer getConfigAsInteger(ConfigEnum configEnum) {
        String configValue = this.getConfigValue(configEnum);
        if (StringUtils.isBlank(configValue)) {
            configValue = configEnum.getValue();
        } else {
            configValue = configValue.trim();
        }

        try {
            return Integer.valueOf(configValue);
        } catch (NumberFormatException var4) {
            log.warn("Invalid setting Integer value : {}", configValue);
            return Integer.valueOf(configEnum.getValue());
        }
    }


    public List<DocuSignLog> getPendingDocuSignLogs(Integer size, long minPk, String status, Date createDateLimit) {
        CriteriaQuery<DocuSignLog> criteriaQuery = criteriaBuilder.createQuery(DocuSignLog.class);
        Root<DocuSignLog> root = criteriaQuery.from(DocuSignLog.class);
        Predicate statusCondition = criteriaBuilder.equal(root.get(DocuSignLog_.status), status);
        Predicate minPkCondition = criteriaBuilder.gt(root.get(DocuSignLog_.pk), minPk);
        Predicate dateCondition = criteriaBuilder.lessThanOrEqualTo(root.get(DocuSignLog_.createDate), createDateLimit);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.and(dateCondition, minPkCondition, statusCondition));

        return entityManager.createQuery(criteriaQuery)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
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
