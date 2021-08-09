package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.models.Agent;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.AgentDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import java.util.List;

public interface AgentService {

  AgentDto createAgent(AgentDto agentDto);

  List<Agent> getAllAgent();
}
