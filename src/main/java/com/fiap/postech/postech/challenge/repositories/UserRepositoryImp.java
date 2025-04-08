package com.fiap.postech.postech.challenge.repositories;

import com.fiap.postech.postech.challenge.dtos.AddressDTO;
import com.fiap.postech.postech.challenge.dtos.LoginRequestDTO;
import com.fiap.postech.postech.challenge.dtos.UserResponseDTO;
import com.fiap.postech.postech.challenge.repositories.entities.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository{

    private final JdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;

    public UserRepositoryImp(JdbcTemplate jdbcTemplate, JdbcClient jdbcClient){
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcClient = jdbcClient;
    }

    public Integer updatePassword(Long id, LoginRequestDTO request) {
        String sql = "UPDATE user_challenge SET senha = ? WHERE id = ?";
        return jdbcTemplate.update(sql, request.senha(), id);
    }

    public Integer updateUser(Long id, UserResponseDTO userResponseDTO) {
        String sql = "UPDATE user_challenge SET nome = ?, email = ?, cep = ?, cidade = ?, rua = ? WHERE id = ?";
        Optional<Address> addressOpt = Optional.ofNullable(userResponseDTO.endereco());

        String cep = addressOpt.map(Address::getCep).orElse(null);
        String cidade = addressOpt.map(Address::getCidade).orElse(null);
        String rua = addressOpt.map(Address::getRua).orElse(null);

        return jdbcTemplate.update(sql,
                userResponseDTO.nome(),
                userResponseDTO.email(),
                cep,
                cidade,
                rua,
                id
        );
    }

    public Integer deleteUser(Long id) {
        String sql = "DELETE FROM user_challenge WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Map<String, Object> selectLogin(String login) {
        String sql = "SELECT * FROM user_challenge WHERE login = ?";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, login);
        return results.isEmpty() ? null : results.get(0);
    }

    public boolean existLogin(String login) {
        String sql = "SELECT COUNT(*) FROM user_challenge WHERE login = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, login);
        return count != null && count > 0;
    }

    public void insertUser(String nome, String email, String login, String senhaCriptografada) {
        String sql = "INSERT INTO user_challenge (nome, email, login, senha) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, nome, email, login, senhaCriptografada);
    }

    public List<UserResponseDTO> findAll(int size, int offset){
        return this.jdbcClient
                .sql("SELECT * FROM user_challenge LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query((rs, rowNum) -> {
                    Address endereco = new Address(
                            rs.getString("cep"),
                            rs.getString("cidade"),
                            rs.getString("rua")
                    );
                    return new UserResponseDTO(
                            rs.getString("nome"),
                            rs.getString("email"),
                            endereco
                    );
                })
                .list();
    }

    public Optional<UserResponseDTO> findById(Long id){
        String sql = "SELECT * FROM user_challenge WHERE id = ?";
        return jdbcClient.sql("SELECT * FROM user_challenge WHERE id = :id")
                .param("id", id)
                .query(UserResponseDTO.class)
                .optional();
    }


}
