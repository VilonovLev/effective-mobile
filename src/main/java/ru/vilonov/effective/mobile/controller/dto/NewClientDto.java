package ru.vilonov.effective.mobile.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientContactDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientInfoDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.UserDto;

import java.math.BigDecimal;

public record NewClientDto(@Valid UserDto user,
                           @Valid ClientContactDto contact,
                           @Valid ClientInfoDto info,
                           @NotNull @Min(value = 250, message = "{error.validation.score.min}") BigDecimal score) {
}
