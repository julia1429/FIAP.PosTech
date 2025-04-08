package com.fiap.postech.postech.challenge.repositories.entities;

import com.fiap.postech.postech.challenge.dtos.AddressDTO;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    private String cep;
    private String cidade;
    private String rua;

    public Address(AddressDTO endereco) {
        this.cep = endereco.cep();
        this.cidade = endereco.cidade();
        this.rua = endereco.rua();
    }
}
