package com.example.equipment_booker.service;

import com.example.equipment_booker.dto.CoordinatesDTO;
import com.example.equipment_booker.model.Company;
import com.example.equipment_booker.model.Contract;
import com.example.equipment_booker.model.ContractEquipment;
import com.example.equipment_booker.model.Equipment;
import com.example.equipment_booker.repository.CompanyRepository;
import com.example.equipment_booker.repository.ContractRepository;
import com.example.equipment_booker.repository.EquipmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Value("${myqueue2}")
    String queue2;
    @Value("${myqueue4}")
    String queue4;
    @Value("${myqueue6}")
    String queue6;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private final List<ArrayList<Double>> points = new ArrayList<>();

    @RabbitListener(queues="${myqueue}")
    public void handleContract(String jsonContract) throws JsonProcessingException {
        Contract contract = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(jsonContract, Contract.class);
        System.out.println("Consumer> " + contract.getHospital().getName());
        for (Contract c: contractRepository.findAll()) {
            if (c.getHospital().getName().equals(contract.getHospital().getName())) {
                contractRepository.delete(c);
                contract.getHospital().getAddress().setId(0L);
                contractRepository.save(contract);
                return;
            }
        }
        contract.getHospital().getAddress().setId(0L);
        contractRepository.save(contract);
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendEquipmentMessage() throws JsonProcessingException {
        for (Contract contract: contractRepository.findAll()) {
            Company company = findCompanyWithContractEquipment(contract);
            if (company == null && LocalDateTime.now().plusDays(3).getDayOfMonth() == contract.getDeliveryDate().getDayOfMonth()) {
                String message = "Equipment cannot be delivered to " + contract.getHospital().getName() + " hospital because no one company doesn't have enough equipment.";
                rabbitTemplate.convertAndSend(queue2, new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(message));
                continue;
            }
            if (contract.getDeliveryDate().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
                String equipment = "";
                for (ContractEquipment ce: contract.getContractEquipment()) {
                    equipment += ce.getName() + " - " + ce.getQuantity() + "\n";
                }
                String message = "Equipment is delivered to " + contract.getHospital().getName() + ":\n" + equipment;
                rabbitTemplate.convertAndSend(queue2, new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(message));
                for (ContractEquipment ce: contract.getContractEquipment()) {
                    for (Equipment e: company.getEquipment()) {
                        if (ce.getName().equals(e.getName())) {
                            Equipment eq = equipmentRepository.findById(e.getId()).orElseGet(null);
                            eq.setQuantity(eq.getQuantity() - ce.getQuantity());
                            equipmentRepository.save(eq);
                        }
                    }
                }
                System.out.println("Successful");
            }
        }
    }

    @RabbitListener(queues="${myqueue3}")
    public void handleRequest(String jsonHospital) throws JsonProcessingException {
        String hospitalName = jsonHospital;
        Contract contract = contractRepository.findContractByHospitalName(hospitalName);
        Company company = findCompanyWithContractEquipment(contract);
        if (company == null) {
            String message = "Equipment cannot be delivered to " + contract.getHospital().getName() + " hospital because no one company doesn't have enough equipment.";
            rabbitTemplate.convertAndSend(queue6, new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(message));
            return;
        }
        CoordinatesDTO coordinates = new CoordinatesDTO(company.getAddress().getLatitude(), company.getAddress().getLongitude(), contract.getHospital().getAddress().getLatitude(), contract.getHospital().getAddress().getLongitude());
        rabbitTemplate.convertAndSend(queue4, new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(coordinates));
        for (ContractEquipment ce: contract.getContractEquipment()) {
            for (Equipment e: company.getEquipment()) {
                if (ce.getName().equals(e.getName())) {
                    Equipment eq = equipmentRepository.findById(e.getId()).orElseGet(null);
                    eq.setQuantity(eq.getQuantity() - ce.getQuantity());
                    equipmentRepository.save(eq);
                }
            }
        }
    }

    private Company findCompanyWithContractEquipment(Contract contract) {
        Company company = null;
        for (Company c: companyRepository.findAll()) {
            boolean allEquipmentFound = true;
            for (ContractEquipment ce : contract.getContractEquipment()) {
                boolean equipmentFound = c.getEquipment().stream()
                        .anyMatch(equipment -> equipment.getName().equals(ce.getName()) &&
                                equipment.getQuantity() >= ce.getQuantity());
                if (!equipmentFound) {
                    allEquipmentFound = false;
                    break;
                }
            }
            if (allEquipmentFound) {
                company = c;
                break;
            }
        }

        return company;
    }

    @RabbitListener(queues="${myqueue5}")
    public void handlePoint(String jsonPoint) throws JsonProcessingException {
        ArrayList<Double> point = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(jsonPoint, ArrayList.class);
        points.add(point);
        simpMessagingTemplate.convertAndSend("/socket-publisher", points);
        if (points.size() == 7) {
            points.clear();
        }
    }
}
