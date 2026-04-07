package in.theexplorers.vms.authservice.common.base;
/*
 * Copyright (c) 2026 The Explorers.
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * This is representing a base structure for entities in the application.
 *
 * <p>This class stores id and audit keys.</p>
 *
 * @author Md Wasif Ali
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    /**
     * It represents the unique id of every record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The id of the user who created this record.
     */
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    /**
     * The timestamp when the record was created.
     */
    @CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    /**
     * The id of the user who last updated this record.
     */
    @Column(name = "updated_by")
    private Long updatedBy;

    /**
     * The timestamp when the user record was updated.
     */
    @LastModifiedDate
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    /**
     * Indicates whether the record is active. Default is true.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}