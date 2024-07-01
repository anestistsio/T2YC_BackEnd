package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.SupportAgentDto;
import com.ots.T2YC_SPRING.dto.SupportAgentMiniDto;
import com.ots.T2YC_SPRING.entities.SupportAgent;
import com.ots.T2YC_SPRING.exceptions.ExistingException;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.SupportAgentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupportAgentService {

    private final SupportAgentRepository supportAgentRepository;

    public SupportAgentService(SupportAgentRepository supportAgentRepository) {
        this.supportAgentRepository = supportAgentRepository;
    }


    public List<SupportAgentDto> allSupportAgents(){
         return supportAgentRepository.findAll()
                 .stream()
                 .filter(SupportAgent::isActive)
                 .map(SupportAgentDto::new)
                 .collect(Collectors.toList());
    }

    public SupportAgentDto findSupportAgentById(int id){
        return new SupportAgentDto( supportAgentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Support agent not found with id: " + id)));
    }

    public SupportAgentDto findSupportAgentByEmail(String email){
        return new SupportAgentDto( supportAgentRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Support agent not found with email: " + email)));
    }

    @Transactional
    public SupportAgent createSupportAgent(SupportAgentMiniDto supportAgentMiniDTO){
        SupportAgent newSupportAgent = new SupportAgent(supportAgentMiniDTO);
        if (supportAgentRepository.findByEmail(newSupportAgent.getEmail()).isPresent()){
            throw new ExistingException("This support agent email is used " + newSupportAgent.getEmail());
        }else {
            newSupportAgent.setActive(true);
            return supportAgentRepository.save(newSupportAgent);
        }
    }

    @Transactional
    public void deactivateSupportAgentById(int id){
        SupportAgent deactivatedSupportAgent = supportAgentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Support agent not found with id: " + id));
        deactivatedSupportAgent.setActive(false);
        supportAgentRepository.save(deactivatedSupportAgent);
    }
    @Transactional
    public void deactivateAllSupportAgents(){
        List<SupportAgent> allActiveSupportAgents = supportAgentRepository.findAll()
                .stream()
                .filter(SupportAgent::isActive)
                .toList();
        if(!allActiveSupportAgents.isEmpty()) {
            for (SupportAgent supportAgent : allActiveSupportAgents) {
                supportAgent.setActive(false);
                supportAgentRepository.save(supportAgent);
            }
        }else {
            throw new NotFoundException("There is not any active support agent");
        }
    }

    @Transactional
    public void updateSupportAgent(int id,SupportAgentDto updatedSupportAgentDto){
        Optional<SupportAgent> foundAgent = supportAgentRepository.findById(id);
        if(foundAgent.isPresent()){
            SupportAgent updatedSupportAgent = new SupportAgent(updatedSupportAgentDto);
            updatedSupportAgent.setId(id);
            supportAgentRepository.save(updatedSupportAgent);
        }else {
            throw new NotFoundException("Support agent not found with id: " + id);
        }
    }
}
