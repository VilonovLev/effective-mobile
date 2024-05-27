package ru.vilonov.effective.mobile.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfo {

    @Column(name = "c_birthday", nullable = false)
    private LocalDate birthday;
    @Column(name = "c_first_name", nullable = false)
    private String firstname;
    @Column(name = "c_surname", nullable = false)
    private String surname;
    @Column(name = "c_patronymic", nullable = true)
    private String patronymic;
}
