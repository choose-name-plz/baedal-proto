package com.nameplz.baedal.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "delete_at")
    protected LocalDateTime deleteAt;
    @Column(name = "delete_by")
    protected String deleteUser;
    @Column(name = "updated_at")
    @LastModifiedDate
    protected LocalDateTime updated_at;
    @Column(name = "update_by")
    @LastModifiedBy
    protected String updateUser;
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    protected LocalDateTime createdAt;
    @Column(name = "create_by", updatable = false)
    @CreatedBy
    protected String createUser;

    public void deleteEntity(String username) {
        this.deleteAt = LocalDateTime.now();
        this.deleteUser = username;
    }

}
