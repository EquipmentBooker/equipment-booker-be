package com.example.equipment_booker.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
