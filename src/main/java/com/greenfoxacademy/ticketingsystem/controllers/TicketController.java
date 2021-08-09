package com.greenfoxacademy.ticketingsystem.controllers;

import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByAgentDto;
import com.greenfoxacademy.ticketingsystem.services.TicketService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TicketController {

@Autowired
  TicketService ticketService;


  @GetMapping("/tickets")
  @ApiOperation(value = "Lists all tickets")
  public ResponseEntity<List<TicketDto>> listTickets() {
    return ResponseEntity.ok(ticketService.getAllTickets());
  }
  @GetMapping("/ticketsInticket")
  @ApiOperation(value = "Lists all tickets in ticket object")
  public ResponseEntity<List<Ticket>> listTicketsInticket() {
    return ResponseEntity.ok(ticketService.getAllTicketsInTicket());
  }

  @ApiOperation(value = "Create a new Ticket")
  @PostMapping("/ticket/create")
  public ResponseEntity<TicketDto> createAccount(@Valid @RequestBody TicketDto ticketDto)
      throws URISyntaxException {
    TicketDto ticketDto1 = ticketService.createTicket(ticketDto);

    return ResponseEntity.created(new URI("/api/user/" +
        ticketDto1.getId()))
        .body(ticketDto1);
  }
  @ApiOperation(value = "Modify a ticket")
  @PatchMapping(value = "ticket/patch/{code}", consumes = "application/json-patch+json")
  public ResponseEntity<TicketDto> updateTicket(@PathVariable String code, @RequestBody @Valid
      TicketUpdateByAgentDto ticketDto) {

    return new ResponseEntity<>(ticketService.updateTicket(ticketDto,code), HttpStatus.NO_CONTENT);
  }

}
