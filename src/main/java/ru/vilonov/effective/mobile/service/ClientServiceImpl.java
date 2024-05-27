package ru.vilonov.effective.mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vilonov.effective.mobile.controller.dto.ClientDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientContactDto;
import ru.vilonov.effective.mobile.controller.dto.embedded.ClientInfoDto;
import ru.vilonov.effective.mobile.model.Client;
import ru.vilonov.effective.mobile.model.embedded.ClientContact;
import ru.vilonov.effective.mobile.model.embedded.ClientInfo;
import ru.vilonov.effective.mobile.repository.ClientRepository;
import ru.vilonov.effective.mobile.repository.JpaSpecificationForClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public Client createClient(ClientContactDto contact, ClientInfoDto info, BigDecimal score) {
        Client client = Client.builder()
                .clientContact(ClientContact.builder().email(contact.email()).phone(contact.phone()).build())
                .clientInfo(ClientInfo.builder()
                        .birthday(info.birthday())
                        .firstname(info.firstname())
                        .surname(info.surname())
                        .patronymic(info.patronymic())
                        .build())
                .score(score)
                .build();
        return this.clientRepository.save(client);
    }

    @Override
    public Client findClientByUuid(UUID uuid) {
        return this.clientRepository.findById(uuid).orElseThrow();
    }

    @Override
    @Transactional
    public Client updateClientContact(UUID uuid, ClientContactDto contactDto) {
        Client client = this.clientRepository.findById(uuid).orElseThrow();
        ClientContact contact = ClientContact.builder()
                .phone(contactDto.phone())
                .email(contactDto.email())
                .build();
        client.setClientContact(contact);
        return client;
    }

    @Override
    @Transactional
    public List<ClientDto> getPageClients(Pageable pageable, Map<String,String> filters) {
        JpaSpecificationForClient specification = new JpaSpecificationForClient(filters);
        Page<Client> clients = this.clientRepository.findAll(specification, pageable);
        return clients.stream().map(ClientDto::of).toList();
    }

    @Override
    public void updateAllBalances(long maxPercentage, long percentage) {
        log.info("client service update balances");
        this.clientRepository.updateBalances(maxPercentage, percentage);
    }

    @Override
    @Transactional
    public Client remittance(Client sender, Client recipient, BigDecimal score) {
        sender.setScore(sender.getScore().subtract(score));
        recipient.setScore(recipient.getScore().add(score));
        return sender;
    }

}
