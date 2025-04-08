package com.fiap.postech.postech.challenge.repositories;

import com.fiap.postech.postech.challenge.dtos.LoginRequestDTO;
import com.fiap.postech.postech.challenge.dtos.UserResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {
    Integer updatePassword(Long id, LoginRequestDTO request);
    Integer updateUser(Long id, UserResponseDTO UserResponseDTO);
    Integer deleteUser(Long id);
    Map<String, Object> selectLogin(String login);
    boolean existLogin(String login);
    void insertUser(String nome, String email, String login, String senhaCriptografada);
    List<UserResponseDTO> findAll(int size, int offset);
    Optional<UserResponseDTO> findById(Long id);
}
