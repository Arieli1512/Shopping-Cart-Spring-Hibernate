package com.jakubw.zaip.Services;

import com.jakubw.zaip.Models.Users;
import com.jakubw.zaip.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = clientRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " //
                    + username + " was not found in the database");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

    public void updateUser(Users user) {
        Users existingUser = clientRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            clientRepository.save(existingUser);
        }
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public void save(Users user) {
        clientRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return clientRepository.findAll();
    }

    public Optional<Users> findById(Long id) {
        return clientRepository.findById(id);
    }

    public void addUser(Users user) {
        Users userA = user;
        userA.setPassword(passwordEncoder.encode(user.getPassword()));
        clientRepository.save(user);
    }
}