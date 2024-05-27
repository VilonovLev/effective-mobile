package ru.vilonov.effective.mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vilonov.effective.mobile.controller.dto.ClientDto;
import ru.vilonov.effective.mobile.controller.dto.NewClientDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientContactDto;
import ru.vilonov.effective.mobile.model.AppUser;
import ru.vilonov.effective.mobile.model.Client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class StorageFacadeImpl implements StorageFacade{

    private final ClientService clientService;
    private final AppUserService userService;

    @Override
    @Transactional
    public ClientDto createClient(NewClientDto newClientDto) {
        Client client = this.clientService
                .createClient(newClientDto.contact(),newClientDto.info(),newClientDto.score());
        AppUser appUser = this.userService
                .createUser(newClientDto.user());
        appUser.setInitScore(newClientDto.score());
        appUser.setClientUuid(client.getUuid());
        return ClientDto.of(client);
    }

    @Override
    @Transactional
    public ClientDto updateClientContact(ClientContactDto contactDto, UserDetails user) {
        AppUser appUser = userService.findByUsername(user.getUsername());
        Client client = this.clientService.updateClientContact(appUser.getClientUuid(),contactDto);
        return ClientDto.of(client);
    }

    @Override
    @Transactional
    public List<ClientDto> getFilteredPage(int numberPage, int sizePage, Map<String, String> filters) {
        PageRequest pageable = PageRequest.of(numberPage, sizePage);
        return this.clientService.getPageClients(pageable,filters);
    }

    @Override
    @Transactional
    public ClientDto remittance(String senderUsername, UUID recipientUuid, BigDecimal score) {
        log.trace("StorageFacadeImpl start remittance");
        AppUser appUser = this.userService.findByUsername(senderUsername);
        Client sender = this.clientService.findClientByUuid(appUser.getClientUuid());
        Client recipient = this.clientService.findClientByUuid(recipientUuid);
        if (sender.getScore().compareTo(score) >= 0) {
            Client result = this.clientService.remittance(sender, recipient, score);
            log.trace("remittance possible");
            return ClientDto.of(result);
        } else {
            log.warn("insufficient funds in the account");
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public void updateAllBalances(long maxPercentage, long percentage) {
        this.clientService.updateAllBalances(maxPercentage, percentage);
    }


}
