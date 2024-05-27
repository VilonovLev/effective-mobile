package ru.vilonov.effective.mobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.vilonov.effective.mobile.controller.dto.ClientDto;
import ru.vilonov.effective.mobile.service.StorageFacade;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("operation")
@Tag(name = "operation-rest-controller")
public class OperationController {
    private final StorageFacade storageFacade;
    @Value("${effective.mobile.score.maxPercentage:150}")
    private long maxPercentage;
    @Value("${effective.mobile.score.percentage:5}")
    private long percentage;

    //Регулярное выражение валидирующее UUID: ^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$
    @PostMapping("/{uuid:^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$}")
    @Operation(summary = "Money transfer between clients")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "400",description = "Bad request",content = @Content),
            @ApiResponse(responseCode = "404",description = "Not Found",content = @Content),
            @ApiResponse(responseCode = "401",description = "Unauthorized",content = @Content)})
    public ResponseEntity<ClientDto> remittance(
            @PathVariable("uuid") UUID recipientUuid,
            @NotNull @RequestParam(name = "score", required = true) BigDecimal score,
            @AuthenticationPrincipal UserDetails user) {
        log.info("remittance request from {}", user.getUsername());
        return ResponseEntity.ok(storageFacade.remittance(user.getUsername(), recipientUuid, score));
    }

    @Scheduled(fixedRate = 60_000, initialDelay =  60_000)
    public void updateBalances() {
        log.info("OperationController start update balances");
        storageFacade.updateAllBalances(maxPercentage,percentage);
    }
}
