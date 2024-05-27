package ru.vilonov.effective.mobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.vilonov.effective.mobile.controller.dto.*;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientContactDto;
import ru.vilonov.effective.mobile.service.StorageFacade;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "client-rest-controller")
@RequestMapping("client")
public class ClientController {

    private final StorageFacade storageFacade;

    @PostMapping
    @Operation(summary = "Create new client")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "Create",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "400",description = "Bad request",content = @Content)})
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody NewClientDto clientDto) {
        log.info("request add {} to clients", clientDto.contact().toString());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.storageFacade.createClient(clientDto));
    }

    @PutMapping
    @Operation(summary = "Update client contact")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "404",description = "Not Found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad request",content = @Content),
            @ApiResponse(responseCode = "401",description = "Unauthorized",content = @Content)})
    public ResponseEntity<ClientDto> updateClientContact(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody ClientContactDto contactDto) {
        log.info("request to update contact {}", user.getUsername());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.storageFacade.updateClientContact(contactDto, user));
    }

    @GetMapping("/{page:\\d*}")
    @Operation(summary = "Get filtered page clients")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ClientDto.class)))),
            @ApiResponse(responseCode = "400",description = "Bad request",content = @Content),
            @ApiResponse(responseCode = "404",description = "Not Found",content = @Content),
            @ApiResponse(responseCode = "401",description = "Unauthorized",content = @Content)})
    public ResponseEntity<List<ClientDto>> getClients(
            @PathVariable(name = "page") int numberPage,
            @Value("${effective.mobile.pages.size:10}") int sizePage,
            @RequestParam(required = false) Map<String,String> filters
    ) {
        log.info("request to get clients page:");
        return ResponseEntity.ok(this.storageFacade.getFilteredPage(numberPage, sizePage, filters));
    }
}
