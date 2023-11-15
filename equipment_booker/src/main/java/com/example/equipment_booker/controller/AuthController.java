package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.JwtAuthenticationRequest;
import com.example.equipment_booker.dto.JwtAuthenticationResponse;
import com.example.equipment_booker.dto.RegisteredUserDTO;
import com.example.equipment_booker.exception.ResourceConflictException;
import com.example.equipment_booker.model.Address;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.service.AuthService;
import com.example.equipment_booker.service.RegisteredUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/auth")
public class AuthController {

    @Autowired
    private RegisteredUserService registeredUserService;
    @Autowired
    private AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<RegisteredUserDTO> saveRegisteredUser(@RequestBody RegisteredUserDTO registeredUserDTO) {

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setName(registeredUserDTO.getName());
        registeredUser.setSurname(registeredUserDTO.getSurname());
        registeredUser.setEmail(registeredUserDTO.getEmail());
        registeredUser.setPassword(passwordEncoder.encode(registeredUserDTO.getPassword()));
        registeredUser.setPhoneNumber(registeredUserDTO.getPhoneNumber());
        registeredUser.setProfession(registeredUserDTO.getProfession());
        registeredUser.setCompanyInfo(registeredUserDTO.getCompanyInfo());
        registeredUser.setConfirmationToken(UUID.randomUUID().toString());
        registeredUser.setActivated(registeredUserDTO.isActivated());
        registeredUser.setAddress(new Address(registeredUserDTO.getAddress()));

        RegisteredUser existUser = registeredUserService.findByEmail(registeredUser.getEmail());

        if (existUser != null) {
            throw new ResourceConflictException(registeredUser.getId(), "E-mail already exists.");
        }

        registeredUser = registeredUserService.save(registeredUser);

        authService.sendConfirmationEmail(registeredUser);

        return new ResponseEntity<>(new RegisteredUserDTO(registeredUser), HttpStatus.CREATED);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam String token) {
        if (authService.confirmAccount(token)) {
            return ResponseEntity.ok("Account successfully confirmed.");
        }
        return ResponseEntity.badRequest().body("Invalid confirmation token.");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticate(
            @RequestBody JwtAuthenticationRequest request) {
        return new ResponseEntity<>(authService.authenticate(request), HttpStatus.OK);
    }

}
