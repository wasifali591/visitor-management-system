package in.theexplorers.vms.authservice.role.entity;

import in.theexplorers.vms.authservice.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a system role.
 *
 * Example: ADMIN, SECURITY, GATE_OPERATOR
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

}