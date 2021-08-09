package com.greenfoxacademy.ticketingsystem.controllers;


import com.greenfoxacademy.ticketingsystem.models.Agent;
import com.greenfoxacademy.ticketingsystem.models.dtos.AgentDto;
import com.greenfoxacademy.ticketingsystem.services.AgentService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AgentController {

  @Autowired
  AgentService agentService;



  @GetMapping("/agents")
  @ApiOperation(value = "Lists all agent")
  public ResponseEntity<List<Agent>> listAllAgent() {
    return ResponseEntity.ok(agentService.getAllAgent());
  }

  @ApiOperation(value = "Add a new Agent")
  @PostMapping("/agent/add")
  public ResponseEntity<AgentDto> createAgent(@Valid @RequestBody AgentDto agentDto)
      throws URISyntaxException {
    AgentDto savedDto = agentService.createAgent(agentDto);

    return ResponseEntity.created(new URI("/api/agent/" +
        savedDto.getId()))
        .body(savedDto);
  }

}
