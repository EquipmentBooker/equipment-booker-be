package com.example.equipment_booker.service;

import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.repository.RegisteredUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisteredUserService {
    @Autowired
    private RegisteredUserRepository registeredUserRepository;
    @Cacheable("user")
    public RegisteredUser findByEmail(String email) {
        return registeredUserRepository.findByEmail(email);
    }
    public List<RegisteredUser> findAll() {
        return registeredUserRepository.findAll();
    }
    public RegisteredUser save(RegisteredUser registeredUser) {
        return registeredUserRepository.save(registeredUser);
    }
    public RegisteredUser findOne(Long id) {
        return registeredUserRepository.findById(id).orElseGet(null);
    }
}
