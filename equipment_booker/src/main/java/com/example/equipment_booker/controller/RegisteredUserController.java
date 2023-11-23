package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.RegisteredUserDTO;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.service.RegisteredUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/registered_users")
@SecurityRequirement(name = "bearerAuth")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;

    @GetMapping
    public ResponseEntity<List<RegisteredUserDTO>> getRegisteredUsers() {

        List<RegisteredUser> registeredUsers = registeredUserService.findAll();

        List<RegisteredUserDTO> registeredUsersDTO = new ArrayList<>();
        for (RegisteredUser ru: registeredUsers) {
            registeredUsersDTO.add(new RegisteredUserDTO(ru));
        }

        return new ResponseEntity<>(registeredUsersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RegisteredUserDTO> getRegisteredUser(@PathVariable Long id) {

        RegisteredUser registeredUser = registeredUserService.findOne(id);

        if (registeredUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(registeredUser);

        return new ResponseEntity<>(registeredUserDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<RegisteredUserDTO> getRegisteredUserByEmail(@PathVariable String email) {
        RegisteredUser registeredUser = registeredUserService.findByEmail(email);

        if (registeredUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(registeredUser);

        return new ResponseEntity<>(registeredUserDTO, HttpStatus.OK);
    }
}
