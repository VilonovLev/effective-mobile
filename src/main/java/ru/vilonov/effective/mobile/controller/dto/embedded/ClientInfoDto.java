package ru.vilonov.effective.mobile.controller.dto.embedded;

import jakarta.validation.constraints.NotNull;
import ru.vilonov.effective.mobile.model.embedded.ClientInfo;

import java.time.LocalDate;

public record ClientInfoDto(@NotNull LocalDate birthday,
                            @NotNull  String firstname,
                            @NotNull  String surname,
                            @NotNull String patronymic) {

    public static ClientInfoDto of(ClientInfo clientInfo) {
        return new ClientInfoDto(clientInfo.getBirthday(),
                clientInfo.getFirstname(),
                clientInfo.getSurname(),
                clientInfo.getPatronymic());
    }
}
