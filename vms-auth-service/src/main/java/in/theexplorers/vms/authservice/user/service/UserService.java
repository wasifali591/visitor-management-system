package in.theexplorers.vms.authservice.user.service;

import in.theexplorers.vms.authservice.user.entity.User;

import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User getById(Long id);

}