package ru.vilonov.effective.mobile.model.embedded;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientContact {

    @Column(name = "c_phone", unique = true, length = 16)
    private String phone;
    @Column(name = "c_email", unique = true, length = 64)
    private String email;

}
