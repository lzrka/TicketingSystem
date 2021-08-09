package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.exceptions.BadResourceException;
import com.greenfoxacademy.ticketingsystem.exceptions.ResourceAlreadyExistsException;
import com.greenfoxacademy.ticketingsystem.exceptions.ResourceNotFoundException;
import com.greenfoxacademy.ticketingsystem.models.Agent;
import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.Status;
import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.SelfServiceTicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByAgentDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByUserDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import com.greenfoxacademy.ticketingsystem.repositories.AgentRepository;
import com.greenfoxacademy.ticketingsystem.repositories.TicketRepository;
import com.greenfoxacademy.ticketingsystem.repositories.UserRepository;
import com.greenfoxacademy.ticketingsystem.services.validator.TicketUpdateValidator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{

  @Autowired
  TicketRepository ticketRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  AgentRepository agentRepository;

  @Autowired
  TicketMapper ticketMapper;

  private boolean existsById(Long id) {
    return ticketRepository.existsById(id);
  }

  @Override
  public List<Ticket> getAllTicketsInTicket() {
    return ticketRepository.findAll();
  }

  @Override
  public List<TicketDto> getAllTickets() {
    return ticketRepository.findAll().stream().map(Ticket::convertToDto).collect(Collectors.toList());
  }

  @Override
  public TicketDto createTicket(TicketDto ticketDto) {
    Objects.requireNonNull(ticketDto);
    if (ticketDto.getId() != null && existsById(ticketDto.getId())) {
      throw new ResourceAlreadyExistsException(
          "Ticket with id: " + ticketDto.getId() + " already exists");
    }
    enumCheck(ticketDto.getPriority());

    Long userId = Long.parseLong(ticketDto.getUserId());
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not exist, Please provide an existing one!"));
    Agent agent = agentRepository.findById(ticketDto.getAssigne_id()).orElseThrow(() -> new ResourceNotFoundException("Agent not exist, Please provide an existing one!"));
    Ticket convertedTicket = ticketDto.convertToTicket(agent,user);


    while (ticketRepository.existsByCode(convertedTicket.getCode())) {
      convertedTicket.setCode(convertedTicket.generateCode());
    }
    convertedTicket.addUser(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not exist, Please provide an existing one!")));
    ticketRepository.save(convertedTicket);

    return convertedTicket.convertToDto();
  }

  @Override
  @Transactional
  public TicketDto updateTicket(TicketUpdateByAgentDto ticketDto, String ticketNumber) {
    Objects.requireNonNull(ticketDto);
    Objects.requireNonNull(ticketNumber);
    System.out.println(ticketDto.getSolution());
    TicketUpdateValidator.validate(ticketDto);



    Ticket ticket = ticketRepository.findByCode(ticketNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not exist, Please provide an existing one!"));
    Agent agent = agentRepository.findById(Long.parseLong(ticketDto.getAssignee_id()))
        .orElseThrow(() -> new ResourceNotFoundException("Agent not exist, Please provide an existing one!"));
    Agent agentToRefresh = agentRepository.findById(ticket.getAssigne().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Agent not exist, Please provide an existing one!"));
    agentToRefresh.getAssigned().removeIf(x -> x.getCode().equals(ticket.getCode()));

    ticketMapper
        .updateTicketFromUpdatebyAgentDto(ticketDto, ticket);
    ticket.setAssigne(agent);

    ticketRepository.save(ticket);
    TicketDto ticketDto1 = ticket.convertToDto();
    ticketDto1.setLastUpdate(ticketDto.getUpdate());

    return ticketDto1;

  }

  public static boolean enumCheck(String string) {


    if (string.equals("Low") || string.equals("Medium") || string.equals("High")  )
    return true;
    else
      throw new BadResourceException("Priority type not exist");
  }



}
