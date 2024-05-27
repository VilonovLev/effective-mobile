package ru.vilonov.effective.mobile.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ru.vilonov.effective.mobile.model.embedded.ClientContact;
import ru.vilonov.effective.mobile.model.embedded.ClientInfo;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "public",name = "t_client")
public class Client {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", type = org.hibernate.id.UUIDGenerator.class)
    @Column(name = "c_client_id", columnDefinition = "uuid", updatable = false, unique = true)
    private UUID uuid;
    @Embedded
    private ClientContact clientContact;
    @Embedded
    private ClientInfo clientInfo;
    @Column(name = "c_score", nullable = false)
    @Positive
    private BigDecimal score;

}
