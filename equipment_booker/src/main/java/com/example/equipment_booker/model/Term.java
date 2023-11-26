package com.example.equipment_booker.model;

import com.example.equipment_booker.dto.TermDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="terms")
public class Term {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "duration")
    private int duration;

    @Column(name = "status")
    private String status;

    @Column(name = "is_predefined")
    private boolean isPredefined;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_administrator_id", referencedColumnName = "id")
    private CompanyAdministrator companyAdministrator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registered_user_id", referencedColumnName = "id")
    private RegisteredUser registeredUser;

    @OneToMany(mappedBy = "term", fetch = FetchType.LAZY)
    private List<TermEquipment> termEquipment = new ArrayList<>();

    @Version
    private Integer version;

    public Term(TermDTO term) {
        this.id = term.getId();
        this.startTime = term.getStartTime();
        this.duration = term.getDuration();
        this.status = term.getStatus();
        this.isPredefined = term.isPredefined();
        this.companyAdministrator = new CompanyAdministrator(term.getCompanyAdministrator());
        this.registeredUser = new RegisteredUser(term.getRegisteredUser());
    }
}
