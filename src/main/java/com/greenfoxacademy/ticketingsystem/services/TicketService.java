package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.dtos.DeviceDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByAgentDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByUserDto;
import java.util.List;

public interface TicketService {

  List<Ticket> getAllTicketsInTicket();

  List<TicketDto> getAllTickets();

   TicketDto createTicket(TicketDto ticketDto);

  TicketDto updateTicket(TicketUpdateByAgentDto ticketDto, String ticketNumber);
}
