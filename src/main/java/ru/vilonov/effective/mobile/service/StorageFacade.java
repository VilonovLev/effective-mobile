package ru.vilonov.effective.mobile.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.vilonov.effective.mobile.controller.dto.ClientDto;
import ru.vilonov.effective.mobile.controller.dto.NewClientDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientContactDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface StorageFacade {

    ClientDto createClient(NewClientDto newClientDto);

    ClientDto updateClientContact(ClientContactDto contactDto, UserDetails user);

    List<ClientDto> getFilteredPage(int numberPage, int sizePage, Map<String, String> filters);

    ClientDto remittance(String senderUsername, UUID recipientUuid, BigDecimal score);

    void updateAllBalances(long maxPercentage, long percentage);
}
