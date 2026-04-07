package in.theexplorers.vms.authservice.permission.entity;

import in.theexplorers.vms.authservice.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a permission that can be assigned to roles.
 *
 * Example: CREATE_VISITOR, APPROVE_PASS
 */
@Getter
@Setter
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

}