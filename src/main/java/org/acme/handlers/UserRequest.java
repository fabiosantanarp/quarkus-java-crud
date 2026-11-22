package org.acme.handlers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.acme.model.UserModel;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * User
 */
public record UserRequest(
        @NotNull @NotBlank(message = "O nome é obrigatório") String name,
        @NotBlank(message = "O email é obrigatório") @NotNull String email,
        @NotNull String id,
        LocalDateTime created_at,
        LocalDateTime update_at) {

    public UserModel toUserModel() {
        return new UserModel(
                this.id != null ? this.id : UUID.randomUUID().toString(),
                this.name,
                this.email,
                this.created_at != null ? this.created_at : LocalDateTime.now(),
                this.update_at != null ? this.update_at : LocalDateTime.now());
    }
}
