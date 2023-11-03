package com.example.equipment_booker.controller;

import com.example.equipment_booker.dto.RegisteredUserDTO;
import com.example.equipment_booker.model.Address;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/registered_users")
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
}
