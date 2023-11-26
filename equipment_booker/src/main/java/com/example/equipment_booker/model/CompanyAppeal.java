package com.example.equipment_booker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="company_appeals")
public class CompanyAppeal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "answer")
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registered_user_id")
    private RegisteredUser registeredUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Version
    private Integer version;
}
