package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.ProductMiniDto;
import com.ots.T2YC_SPRING.dto.RatingDto;
import com.ots.T2YC_SPRING.dto.SupportAgentDto;
import com.ots.T2YC_SPRING.dto.SupportAgentMiniDto;
import com.ots.T2YC_SPRING.entities.Product;
import com.ots.T2YC_SPRING.entities.Rating;
import com.ots.T2YC_SPRING.entities.SupportAgent;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.SupportAgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupportAgentServiceTest {

    @Mock
    private SupportAgentRepository supportAgentRepository;

    @InjectMocks
    private SupportAgentService supportAgentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void allSupportAgents() {
        SupportAgent agent1 = SupportAgent.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .yearsOfExperience(1)
                .productThatAgentSupports(List.of(new Product(12,"Product_1",true,List.of())))
                .ratings(List.of(new Rating(12,2,"",new SupportAgent())))
                .isActive(true)
                .build();
        SupportAgent agent2 = SupportAgent.builder()
                .id(2)
                .firstName("testFirstName2")
                .lastName("testLastName2")
                .email("testEmail2")
                .yearsOfExperience(2)
                .productThatAgentSupports(List.of(new Product(13,"Product_13",true,List.of())))
                .ratings(List.of(new Rating(13,3,"",new SupportAgent())))
                .isActive(true)
                .build();
        List<SupportAgent> agents = Arrays.asList(agent1, agent2);

        when(supportAgentRepository.findAll()).thenReturn(agents);

        List<SupportAgentDto> result = supportAgentService.allSupportAgents();

        assertEquals(2, result.size());
        verify(supportAgentRepository, times(1)).findAll();
    }

    @Test
    void findSupportAgentById() {
        SupportAgent agent = SupportAgent.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .yearsOfExperience(1)
                .productThatAgentSupports(List.of(new Product(14,"Product_14",true,List.of())))
                .ratings(List.of(new Rating(14,1,"",new SupportAgent())))
                .isActive(true)
                .build();
        agent.setId(1);

        when(supportAgentRepository.findById(1)).thenReturn(Optional.of(agent));

        SupportAgentDto result = supportAgentService.findSupportAgentById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(supportAgentRepository, times(1)).findById(1);
    }

    @Test
    void findSupportAgentById_NotFound() {
        when(supportAgentRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> supportAgentService.findSupportAgentById(1));

        assertEquals("Support agent not found with id: 1", exception.getMessage());
        verify(supportAgentRepository, times(1)).findById(1);
    }

    @Test
    void createSupportAgent() {
        SupportAgentMiniDto supportAgentMiniDto = SupportAgentMiniDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .yearsOfExperience(1)
                .build();
        SupportAgent agent = new SupportAgent(supportAgentMiniDto);

        when(supportAgentRepository.save(any(SupportAgent.class))).thenReturn(agent);

        SupportAgent result = supportAgentService.createSupportAgent(supportAgentMiniDto);

        assertNotNull(result);
        verify(supportAgentRepository, times(1)).save(any(SupportAgent.class));
    }

    @Test
    void deleteSupportAgentById() {
        SupportAgent agent = SupportAgent.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .yearsOfExperience(1)
                .isActive(true)
                .productThatAgentSupports(List.of(new Product(1,"Product_1",true,List.of())))
                .ratings(List.of(new Rating(2,3,"",new SupportAgent())))
                .build();

        when(supportAgentRepository.findById(1)).thenReturn(Optional.of(agent));

        supportAgentService.deactivateSupportAgentById(1);

        verify(supportAgentRepository, times(1)).findById(1);
        verify(supportAgentRepository, times(1)).save(any(SupportAgent.class));
    }

    @Test
    void deleteSupportAgentById_NotFound() {
        when(supportAgentRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> supportAgentService.deactivateSupportAgentById(1));

        assertEquals("Support agent not found with id: 1", exception.getMessage());
        verify(supportAgentRepository, times(1)).findById(1);
        verify(supportAgentRepository, times(0)).deleteById(1);
    }


    @Test
    void updateSupportAgent() {
        SupportAgentDto updatedSupportAgentDto = SupportAgentDto.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .yearsOfExperience(1)
                .productThatAgentSupports(List.of(new ProductMiniDto(15,"Product_15")))
                .ratings(List.of(new RatingDto(15,3,"")))
                .build();

        SupportAgent agent = new SupportAgent(updatedSupportAgentDto);

        when(supportAgentRepository.findById(1)).thenReturn(Optional.of(agent));
        when(supportAgentRepository.save(any(SupportAgent.class))).thenReturn(agent);

        supportAgentService.updateSupportAgent(1, updatedSupportAgentDto);

        verify(supportAgentRepository, times(1)).findById(1);
        verify(supportAgentRepository, times(1)).save(any(SupportAgent.class));
    }

    @Test
    void updateSupportAgent_NotFound() {
        SupportAgentDto updatedSupportAgentDto = SupportAgentDto.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .yearsOfExperience(1)
                .productThatAgentSupports(List.of(new ProductMiniDto(16,"Product_16")))
                .ratings(List.of(new RatingDto(16,3,"")))
                .isActive(true)
                .build();

        when(supportAgentRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> supportAgentService.updateSupportAgent(1, updatedSupportAgentDto));

        assertEquals("Support agent not found with id: 1", exception.getMessage());
        verify(supportAgentRepository, times(1)).findById(1);
        verify(supportAgentRepository, times(0)).save(any(SupportAgent.class));
    }
}
