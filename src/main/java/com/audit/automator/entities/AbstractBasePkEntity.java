package com.audit.automator.entities;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dawuzi
 *
 * @param <T>
 */

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractBasePkEntity<T> implements Serializable, Comparable<AbstractBasePkEntity<T>> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractBasePkEntity.class);

    /**
     *
     */
    private static final long serialVersionUID = -7270322627438965338L;

    @Column(name = "ACTIVE", nullable = false, insertable = true, updatable = true)
    private boolean active = true;

    @Column(name = "DELETED", nullable = false, insertable = true, updatable = true)
    private boolean deleted;

    @Column(name = "CREATE_DATE", nullable = false, insertable = true, updatable = false)
    private Date createDate;

    @Column(name = "LAST_MODIFIED", nullable = false, insertable = true, updatable = true)
    private Date lastModified;

    /**
     * @return the pk
     */
    public abstract T getPk();



    /**
     * @return retrieves the entity table name based on the {@link Table#name()}
     *         name value of the {@link Table} annotation if it exists
     */
    public String getTableName() {

        Table table = getClass().getAnnotation(Table.class);

        if (table == null) {
            return "";
        }

        String tableName = table.name();

        return tableName;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((getPk() == null) ? 0 : getPk().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        AbstractBasePkEntity<T> other = (AbstractBasePkEntity<T>) obj;
        if (getPk() == null || other.getPk() == null) {
            return false;
        }
        return getPk().equals(other.getPk());
    }

    @Override
    public int compareTo(AbstractBasePkEntity<T> other) {

        if (this.getPk() == null) {
            if (other.getPk() == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            // this.getPk() != null

            if (other.getPk() == null) {
                return 1;
            } else {

                if (!Comparable.class.isAssignableFrom(this.getPk().getClass())) {
                    throw new IllegalArgumentException(
                            "Class type of the pk does not implement the Comparable interface. "
                                    + this.getPk().getClass().getName());
                }

                @SuppressWarnings("unchecked")
                Comparable<T> thisComparablePk = (Comparable<T>) this.getPk();

                return thisComparablePk.compareTo(other.getPk());
            }
        }
    }

    @PrePersist
    protected void onCreate() {

        this.createDate = new Date();
        this.lastModified = this.createDate;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModified = new Date();
    }

}
