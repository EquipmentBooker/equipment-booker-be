package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.RegisteredUserDTO;
import com.example.equipment_booker.model.Address;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.service.AuthService;
import com.example.equipment_booker.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/auth")
public class AuthController {

    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<RegisteredUserDTO> saveRegisteredUser(@RequestBody RegisteredUserDTO registeredUserDTO) {

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setName(registeredUserDTO.getName());
        registeredUser.setSurname(registeredUserDTO.getSurname());
        registeredUser.setEmail(registeredUserDTO.getEmail());
        registeredUser.setPassword(registeredUserDTO.getPassword());
        registeredUser.setPhoneNumber(registeredUserDTO.getPhoneNumber());
        registeredUser.setProfession(registeredUserDTO.getProfession());
        registeredUser.setCompanyInfo(registeredUserDTO.getCompanyInfo());
        registeredUser.setConfirmationToken(UUID.randomUUID().toString());
        registeredUser.setActivated(registeredUserDTO.isActivated());
        registeredUser.setAddress(new Address(registeredUserDTO.getAddress()));

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

}
