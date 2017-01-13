package com.example.integration.repository.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Entity for sync date.
 */
@Entity
@Table(name = "lg_rods_tap_sync_time")
public class TapDispatcherSyncDateEntity {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "sync_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncDate;

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString().replace("-", "");
        }
        setCreatedAt(new Date());
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    @Override
    public String toString() {
        return "TapDispatcherSyncDateEntity{" +
                "uuid='" + uuid + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", syncDate=" + syncDate +
                '}';
    }
}