package in.theexplorers.vms.authservice.user.repository;

import in.theexplorers.vms.authservice.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 *
 * @author Md Wasif Ali
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndIsActiveTrue(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<User> findByIsActiveTrue(Pageable pageable);

    Page<User> findByIsActiveFalse(Pageable pageable);

}