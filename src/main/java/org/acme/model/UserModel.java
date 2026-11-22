package org.acme.model;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UniqueElements;

public record UserModel(
        String id,
        String name,
        @UniqueElements String email,
        LocalDateTime created_at,
        LocalDateTime update_at) {
}
