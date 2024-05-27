package ru.vilonov.effective.mobile.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vilonov.effective.mobile.controller.dto.ClientDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientContactDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientInfoDto;
import ru.vilonov.effective.mobile.model.Client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface ClientService {
    Client createClient(ClientContactDto contact, ClientInfoDto info, BigDecimal score);
    Client findClientByUuid(UUID uuid);
    Client updateClientContact(UUID uuid, ClientContactDto contact);
    List<ClientDto> getPageClients(Pageable pageable, Map<String,String> filters);
    void updateAllBalances(long maxPercentage, long percentage);
    Client remittance(Client client, Client res, BigDecimal score);
}
