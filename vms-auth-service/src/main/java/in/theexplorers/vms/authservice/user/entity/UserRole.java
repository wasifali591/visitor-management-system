package in.theexplorers.vms.authservice.user.entity;

import in.theexplorers.vms.authservice.common.base.BaseEntity;
import in.theexplorers.vms.authservice.role.entity.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents mapping between user and role.
 */
@Getter
@Setter
@Entity
@Table(name = "user_roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_role",
                        columnNames = {"user_id", "role_id"}
                )
        }
)
public class UserRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}