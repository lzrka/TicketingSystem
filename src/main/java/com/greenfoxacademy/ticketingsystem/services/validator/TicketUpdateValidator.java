package com.greenfoxacademy.ticketingsystem.services.validator;

import com.greenfoxacademy.ticketingsystem.exceptions.BadResourceException;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByAgentDto;

public class TicketUpdateValidator {

  public static void validate(TicketUpdateByAgentDto ticketUpdate) {

    if(!(ticketUpdate.getSolution() == null) && !ticketUpdate.getStatus().equals("Closed") )
      throw new BadResourceException("If solution field is filled, then status has to be Closed");
    if ((ticketUpdate.getSolution() == null) && ticketUpdate.getStatus().equals("Closed"))
      throw new BadResourceException("Solution has to be filled before closing the ticket");
    }
  }


