package ru.vilonov.effective.mobile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
//import org.springframework.SecurityBeans.core.userdetails.User;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Data
@Builder
@Table(schema = "public",name = "t_user", indexes = @Index(columnList = "c_client_id"))
@NoArgsConstructor
@AllArgsConstructor
public class AppUser{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", type = org.hibernate.id.UUIDGenerator.class)
    @Column(name = "c_user_id", columnDefinition = "uuid", updatable = false)
    private UUID uuid;
    @Column(name = "c_client_id", unique = true)
    private UUID clientUuid;
    @Column(name = "c_username", unique = true, nullable = false)
    private String username;
    @Column(name = "c_password", nullable = false)
    private String password;
    @Column(name = "c_init_score")
    private BigDecimal initScore;
}
