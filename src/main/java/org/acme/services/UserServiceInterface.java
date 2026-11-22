package org.acme.services;

import java.util.Set;

import org.acme.model.UserModel;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface UserServiceInterface {
    UserModel findById(String id);

    Set<UserModel> findAll(Integer page, Integer size);

    UserModel save(UserModel user);
}
