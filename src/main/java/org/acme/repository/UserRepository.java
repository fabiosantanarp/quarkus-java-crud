package org.acme.repository;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.exceptions.CustomException;
import org.acme.exceptions.UserAlreadyFoundException;
import org.acme.model.UserModel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepository implements UserRepositoryInterface {
    private final Set<UserModel> userList = new LinkedHashSet<>();

    public Optional<UserModel> findByEmail(String email) {
        return this.userList.stream()
                .filter(user -> user.email().equalsIgnoreCase(email))
                .findFirst();
    }

    @Transactional
    public Optional<UserModel> addUser(UserModel user) {
        findByEmail(user.email()).ifPresent(u -> {
            throw new UserAlreadyFoundException();
        });

        if (!this.userList.add(user)) {
            throw new CustomException("Failed to add user");
        }
        return Optional.of(user);
    }

    public Set<UserModel> findAll(Integer page, Integer size) {
        if (userList == null || userList.isEmpty()) {
            return Collections.emptySet();
        }

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        return this.userList.stream()
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Optional<UserModel> findById(String id) {
        return this.userList.stream()
                .filter(user -> user.id().equals(id))
                .findFirst();
    }
}
