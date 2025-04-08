package com.fiap.postech.postech.challenge.controllers;

import com.fiap.postech.postech.challenge.dtos.LoginRequestDTO;
import com.fiap.postech.postech.challenge.dtos.UserResponseDTO;
import com.fiap.postech.postech.challenge.dtos.UserDTO;
import com.fiap.postech.postech.challenge.services.userService;
import com.fiap.postech.postech.challenge.repositories.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Controller para CRUD de usuários")

public class userController {


    private final userService service;

    public userController(userService service) {
        this.service = service;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDTO user){
        service.register(new User(user));
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO requestDTO) {
        String response = service.login(requestDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers(@RequestParam("page") int page, @RequestParam("size") int size){
        var listUsers = service.findAllUsers(page, size);
        return ResponseEntity.ok(listUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponseDTO>> findUser(@PathVariable("id") Long id){
        var user = this.service.findById(id);
        return ResponseEntity.ok(user);

    };

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserResponseDTO UserResponseDTO){
        this.service.updateUser(id, UserResponseDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/password")
    @Transactional
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody LoginRequestDTO request){
        service.updatePassword(id, request);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean isDeleted = service.deleteUser(id);
        return isDeleted
                ? ResponseEntity.ok("Usuário deletado com sucesso!")
                : ResponseEntity.notFound().build();
    }
}
