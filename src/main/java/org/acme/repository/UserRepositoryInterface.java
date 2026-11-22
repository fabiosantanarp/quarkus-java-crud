package org.acme.repository;

import java.util.Optional;
import java.util.Set;

import org.acme.model.UserModel;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface UserRepositoryInterface {
    Optional<UserModel> findById(String id);

    Set<UserModel> findAll(Integer page, Integer size);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> addUser(UserModel user);
}
