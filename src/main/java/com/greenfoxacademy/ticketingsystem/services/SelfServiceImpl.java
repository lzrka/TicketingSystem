package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.exceptions.ResourceAlreadyExistsException;
import com.greenfoxacademy.ticketingsystem.exceptions.ResourceNotFoundException;
import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.SelfServiceTicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByUserDto;
import com.greenfoxacademy.ticketingsystem.repositories.TicketRepository;
import com.greenfoxacademy.ticketingsystem.repositories.UserRepository;
import java.util.Objects;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelfServiceImpl implements SelfService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  TicketRepository ticketRepository;
  @Autowired
  TicketMapper ticketMapper;

  private boolean existsById(Long id) {
    return userRepository.existsById(id);
  }

  @Override
  @Transactional
  public TicketDto findTicketByCode(String code) {
      Ticket ticket = ticketRepository.findByCode(code)
          .orElseThrow(() -> new EntityNotFoundException("Ticket not exist, Please provide an existing one!"));
      return ticket.convertToDto();
  }

  @Override
  @Transactional
  public TicketDto createTicket(SelfServiceTicketDto ticketDto) {
    Objects.requireNonNull(ticketDto);
    if (ticketDto.getId() != null && existsById(ticketDto.getId())) {
      throw new ResourceAlreadyExistsException(
          "Ticket with id: " + ticketDto.getId() + " already exists");
    }
    Long userId = Long.parseLong(ticketDto.getUserId());

    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not exist, Please provide an existing one!"));
    Ticket convertedTicket = ticketDto.convertToTicket(user);


  //  while (ticketRepository.existsByCode(convertedTicket.getCode())) {
   //   convertedTicket.setCode(convertedTicket.generateCode());
  //  }
    convertedTicket.addUser(userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not exist, Please provide an existing one!")));
    ticketRepository.save(convertedTicket);

    return convertedTicket.convertToDto();

  }

  @Override
  @Transactional
  public TicketDto updateTicket(TicketUpdateByUserDto ticketDto, String ticketNumber) {
    Objects.requireNonNull(ticketDto);
    Objects.requireNonNull(ticketNumber);

    Ticket ticket = ticketRepository.findByCode(ticketNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not exist, Please provide an existing one!"));

    ticketMapper
        .updateTicketFromUpdateByUserDto(ticketDto, ticket);

    ticketRepository.save(ticket);

    return ticket.convertToDto();

  }
}
