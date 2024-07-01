package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.ProductMiniDto;
import com.ots.T2YC_SPRING.dto.SupportAgentDto;
import com.ots.T2YC_SPRING.dto.SupportAgentMiniDto;
import com.ots.T2YC_SPRING.entities.SupportAgent;
import com.ots.T2YC_SPRING.exceptions.ExistingException;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.SupportAgentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SupportAgentServiceIT {

    @Autowired
    private SupportAgentService supportAgentService;
    @Autowired
    private SupportAgentRepository supportAgentRepository;

    @Test
    void allSupportAgents() {
        List<SupportAgentDto> result = supportAgentService.allSupportAgents();
        int size = supportAgentRepository.findAll()
                .stream()
                .filter(SupportAgent::isActive)
                .toList()
                .size();
        assertEquals(size,result.size());
    }
    @Test
    void findBySupportAgentId() {
        SupportAgentDto result = supportAgentService.findSupportAgentById(2);
        assertNotNull(result);
        assertEquals("Jane",result.getFirstName());
    }
    @Test
    void findSupportAgentById_NotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> supportAgentService.findSupportAgentById(99));
        assertEquals("Support agent not found with id: 99", exception.getMessage());
    }
    @Test
    void createSupportAgent() {
        SupportAgentMiniDto supportAgentMiniDto = SupportAgentMiniDto.builder()
                .firstName("testFirstName1")
                .lastName("testLastName1")
                .email("testEmail1")
                .yearsOfExperience(1)
                .isActive(true)
                .build();
        SupportAgent result = supportAgentService.createSupportAgent(supportAgentMiniDto);
        assertEquals("testFirstName1",result.getFirstName());
    }
    @Test
    void tryToCreateExistingSupportAgent(){
        SupportAgentMiniDto supportAgentMiniDto = SupportAgentMiniDto.builder()
                .firstName("testFirstName1")
                .lastName("testLastName1")
                .email("jane.smith@example.com")
                .yearsOfExperience(1)
                .isActive(true)
                .build();
        ExistingException exception = assertThrows(ExistingException.class, () -> supportAgentService.createSupportAgent(supportAgentMiniDto));
    }
    @Test
    void deactivateSupportAgentById() {
        supportAgentService.deactivateSupportAgentById(1);
        assertFalse(supportAgentRepository.findById(1).orElseThrow(() -> new NotFoundException("")).isActive());

    }
    @Test
    void deactivateSupportAgentById_NotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> supportAgentService.deactivateSupportAgentById(99));
        assertEquals("Support agent not found with id: 99", exception.getMessage());
    }
    @Test
    void deactivateAllSupportAgent() {
        supportAgentService.deactivateAllSupportAgents();
        List<SupportAgent> allSupportAgent = supportAgentRepository.findAll()
                .stream()
                .toList();
        for (SupportAgent supportAgent : allSupportAgent ) {
            assertFalse(supportAgent.isActive());
        }
    }
    @Test
    void updateSupportAgent() {
        SupportAgentDto updatedSupportAgentDto = SupportAgentDto.builder()
                .firstName("testFirstName2")
                .lastName("testLastName2")
                .email("testEmail2")
                .yearsOfExperience(2)
                .productThatAgentSupports(List.of(new ProductMiniDto(18,"Product_18")))
                .isActive(true)
                .build();
        supportAgentService.updateSupportAgent(1, updatedSupportAgentDto);
        SupportAgent supportAgent = supportAgentRepository.findById(1).orElseThrow();
        assertEquals(supportAgent.getFirstName(), updatedSupportAgentDto.getFirstName());
    }
    @Test
    void updateSupportAgent_NotFound() {
        SupportAgentDto updatedSupportAgentDto = SupportAgentDto.builder()
                .firstName("testFirstName3")
                .lastName("testLastName3")
                .email("testEmail3")
                .yearsOfExperience(3)
                .productThatAgentSupports(List.of(new ProductMiniDto(19,"Product_19")))
                .isActive(true)
                .build();
        NotFoundException exception = assertThrows(NotFoundException.class, () -> supportAgentService.updateSupportAgent(99, updatedSupportAgentDto));
        assertEquals("Support agent not found with id: 99", exception.getMessage());
    }
}