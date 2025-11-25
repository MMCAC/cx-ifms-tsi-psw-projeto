package com.financasplus.service;

import com.financasplus.model.User;
import com.financasplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para gerenciar operações de User
 * Contém a lógica de negócio relacionada a usuários
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Busca todos os usuários
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Busca um usuário pelo ID
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Busca um usuário pelo username
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Busca um usuário pelo email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Cria um novo usuário
     */
    public User createUser(User user) {
        // Validações básicas
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username já existe!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email já existe!");
        }
        return userRepository.save(user);
    }

    /**
     * Atualiza um usuário existente
     */
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (userDetails.getFullName() != null) {
            user.setFullName(userDetails.getFullName());
        }
        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new IllegalArgumentException("Email já existe!");
            }
            user.setEmail(userDetails.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * Deleta um usuário
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Verifica se um username já existe
     */
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Verifica se um email já existe
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
