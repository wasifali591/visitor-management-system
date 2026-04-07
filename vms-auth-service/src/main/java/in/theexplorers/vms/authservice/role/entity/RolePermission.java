package in.theexplorers.vms.authservice.role.entity;

import in.theexplorers.vms.authservice.common.base.BaseEntity;
import in.theexplorers.vms.authservice.permission.entity.Permission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents mapping between role and permission.
 */
@Getter
@Setter
@Entity
@Table(name = "role_permissions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_role_permission",
                        columnNames = {"role_id", "permission_id"}
                )
        }
)
public class RolePermission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

}