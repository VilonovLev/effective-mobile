package ru.vilonov.effective.mobile.controller.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientContactDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientInfoDto;
import ru.vilonov.effective.mobile.model.Client;

import java.math.BigDecimal;
import java.util.UUID;

public record ClientDto(@Nullable UUID uuid,
                        ClientContactDto contact,
                        ClientInfoDto info,
                        @Positive BigDecimal score) {
    public static ClientDto of (Client client) {
        return new ClientDto(
                client.getUuid(),
                ClientContactDto.of(client.getClientContact()),
                ClientInfoDto.of(client.getClientInfo()),
                client.getScore());
    }

    public static ClientDto of (NewClientDto client) {
        return new ClientDto(null ,client.contact(), client.info(), client.score());
    }

}
