package org.acme.services;

import java.util.Set;

import org.acme.exceptions.UserNotFoundException;
import org.acme.model.UserModel;
import org.acme.repository.UserRepositoryInterface;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService implements UserServiceInterface {
    @Inject
    UserRepositoryInterface userRepository;

    @Override
    public UserModel findById(String id) {
        return (UserModel) userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public Set<UserModel> findAll(Integer page, Integer size) {
        return userRepository.findAll(page, size);
    }

    @Override
    public UserModel save(UserModel user) {
        userRepository.addUser(user);
        return user;
    }
}