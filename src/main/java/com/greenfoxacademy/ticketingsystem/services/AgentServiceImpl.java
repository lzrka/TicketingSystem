package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.exceptions.ResourceAlreadyExistsException;
import com.greenfoxacademy.ticketingsystem.models.Agent;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.AgentDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import com.greenfoxacademy.ticketingsystem.repositories.AgentRepository;
import com.greenfoxacademy.ticketingsystem.repositories.UserRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl implements AgentService{

  @Autowired
  AgentRepository agentRepository;


  private boolean existsById(Long id) {
    return agentRepository.existsById(id);
  }

  @Override
  public List<Agent> getAllAgent() {
    return agentRepository.findAll();
  }

  @Override
  public AgentDto createAgent(AgentDto agentDto) {
    Objects.requireNonNull(agentDto);
    if (agentDto.getId() != null && existsById(agentDto.getId())) {
      throw new ResourceAlreadyExistsException(
          "Agent with id: " + agentDto.getId() + " already exists");
    }
    Agent convertedAgent = agentDto.convertToAgent();

    agentRepository.save(convertedAgent);

    return convertedAgent.convertToAgentDto();
  }




}
