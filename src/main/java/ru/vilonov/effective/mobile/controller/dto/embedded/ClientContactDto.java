package ru.vilonov.effective.mobile.controller.dto.embedded;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import ru.vilonov.effective.mobile.model.embedded.ClientContact;

public record ClientContactDto(
        @Nullable
        @Pattern(regexp = "^\\d{10}$",
        message = "{error.validation.phone}") String phone,
        @Nullable
        @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$",
        message = "{error.validation.email}") String email) {

    @AssertTrue(message = "{error.validation.contact}")
    public boolean isNotNull() {
        return (phone != null || email != null);
    }
    public static ClientContactDto of(ClientContact clientContact) {
        return new ClientContactDto(clientContact.getPhone(),clientContact.getEmail());
    }
}
