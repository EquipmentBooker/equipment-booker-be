package com.example.equipment_booker.service;

import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredUserService {
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    public List<RegisteredUser> findAll() {
        return registeredUserRepository.findAll();
    }

    public RegisteredUser save(RegisteredUser registeredUser) {
        return registeredUserRepository.save(registeredUser);
    }
}
