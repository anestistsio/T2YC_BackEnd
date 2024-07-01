package com.ots.T2YC_SPRING.controllers;

import com.ots.T2YC_SPRING.dto.SupportAgentDto;
import com.ots.T2YC_SPRING.dto.SupportAgentMiniDto;
import com.ots.T2YC_SPRING.entities.SupportAgent;
import com.ots.T2YC_SPRING.services.SupportAgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/agent")
public class SupportAgentController {

    private final SupportAgentService supportAgentService;

    public SupportAgentController(SupportAgentService supportAgentService) {
        this.supportAgentService = supportAgentService;
    }
    @PreAuthorize("hasRole('SUPPORT_AGENT')")
    @GetMapping("/all")
    public List<SupportAgentDto> getAllSupportAgent(){
        return supportAgentService.allSupportAgents();
    }

    @GetMapping("/{id}")
    public SupportAgentDto getSupportAgentById(@PathVariable int id){
        return supportAgentService.findSupportAgentById(id);
    }

    @GetMapping("/email/{email}")
    public SupportAgentDto getSupportAgentByEmail(@PathVariable String email){
        return supportAgentService.findSupportAgentByEmail(email);
    }

    @PostMapping
    public ResponseEntity<String> addNewSupportAgent(@RequestBody SupportAgentMiniDto supportAgentMiniDTO){
        SupportAgent createdSupportAgent = supportAgentService.createSupportAgent(supportAgentMiniDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSupportAgent.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/deactivate/{id}")
    public void deactivateSupportAgent(@PathVariable int id){
        supportAgentService.deactivateSupportAgentById(id);
    }

    @PutMapping("/deactivate/all")
    public void deactivateAllSupportAgents(){
        supportAgentService.deactivateAllSupportAgents();
    }

    @PutMapping("/{id}")
    public void updateSupportAgent(@PathVariable int id,@RequestBody SupportAgentDto updatedSupportAgentDto){
        supportAgentService.updateSupportAgent(id,updatedSupportAgentDto);
    }

}
