package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.dtos.SelfServiceTicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByUserDto;
import org.springframework.stereotype.Repository;


public interface SelfService {

  public TicketDto createTicket(SelfServiceTicketDto ticketDto);

  public TicketDto updateTicket(TicketUpdateByUserDto ticketDto, String ticketNumber);

  TicketDto findTicketByCode(String code);
}
