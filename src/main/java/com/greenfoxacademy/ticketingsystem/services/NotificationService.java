package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;

public interface NotificationService {

  void notifyUserAboutChanges(TicketDto ticket);
}
