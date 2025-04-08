package com.fiap.postech.postech.challenge.repositories.entities;

import com.fiap.postech.postech.challenge.dtos.LoginRequestDTO;
import com.fiap.postech.postech.challenge.dtos.UserDTO;
import com.fiap.postech.postech.challenge.dtos.UserResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "user_challenge")
@Entity(name = "user_challenge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private Date data_ultima_alteracao;
    @Embedded
    private Address endereco;

    public User(UserDTO user) {
        this.nome = user.nome();
        this.email = user.email();
        this.login = user.login();
        this.senha = user.senha();
        this.data_ultima_alteracao = user.data_ultima_alteracao();
        this.endereco = new Address(user.endereco());
    }

    public User(LoginRequestDTO requestDTO){
        this.login = requestDTO.login();
        this.senha = requestDTO.senha();
    }


}
