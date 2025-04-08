package com.fiap.postech.postech.challenge.services;

import com.fiap.postech.postech.challenge.dtos.LoginRequestDTO;
import com.fiap.postech.postech.challenge.dtos.UserResponseDTO;
import com.fiap.postech.postech.challenge.repositories.UserRepository;
import com.fiap.postech.postech.challenge.repositories.entities.User;
import com.fiap.postech.postech.challenge.repositories.UserRepositoryImp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class userService {
    private final UserRepository userRepository;


    public userService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void updateUser(Long id, UserResponseDTO UserResponseDTO){
        int rowsAffected = userRepository.updateUser(id, UserResponseDTO);
        if (rowsAffected < 0) {
           throw new RuntimeException("Usuário não encontrado!");
        }
    }
    public void updatePassword(Long id, LoginRequestDTO request){
        int rowsAffected = userRepository.updatePassword(id, request);
        if (rowsAffected < 0) {
            throw new RuntimeException("Não foi possível alterar a senha!");
        }
    }

    public boolean deleteUser(Long id) {
        return userRepository.deleteUser(id) > 0;
    }

    public void register(User User) {
        if (userRepository.existLogin(User.getLogin())) {
            throw new RuntimeException("Login já existe!");
        }

        userRepository.insertUser(User.getNome(), User.getEmail(), User.getLogin(), User.getSenha());
    }

    public String login(LoginRequestDTO request) {
        Map<String, Object> user = userRepository.selectLogin(request.login());

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        String senhaBanco = (String) user.get("senha");

        if (!request.senha().equals(senhaBanco)) {
            throw new RuntimeException("Senha incorreta.");
        }

        return "Login realizado com sucesso!";
    }

    public List<UserResponseDTO> findAllUsers(int page, int size){
        int offset = (page - 1) * size;
        return this.userRepository.findAll(size, offset);

    }
    public Optional<UserResponseDTO> findById(Long id){
        return this.userRepository.findById(id);
    }

}
