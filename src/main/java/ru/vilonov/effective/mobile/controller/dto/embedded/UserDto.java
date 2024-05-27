package ru.vilonov.effective.mobile.controller.dto.embedded;

import jakarta.validation.constraints.NotNull;

public record UserDto(@NotNull String username, @NotNull String password) {
}
