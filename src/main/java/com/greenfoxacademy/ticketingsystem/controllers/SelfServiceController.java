package com.greenfoxacademy.ticketingsystem.controllers;

import com.greenfoxacademy.ticketingsystem.models.dtos.SelfServiceTicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByUserDto;
import com.greenfoxacademy.ticketingsystem.services.SelfService;
import com.greenfoxacademy.ticketingsystem.services.TicketService;
import com.greenfoxacademy.ticketingsystem.services.UserService;
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
public class SelfServiceController {

  @Autowired
  SelfService selfService;


  @GetMapping("/selfservice/ticket/{code}")
  @ApiOperation(value = "Return ticket by code")
  public ResponseEntity<TicketDto> listTicketByCode(@PathVariable String code) {
    return ResponseEntity.ok(selfService.findTicketByCode(code));
  }

  @ApiOperation(value = "Create a new Ticket by User")
  @PostMapping("/selfservice/add")
  public ResponseEntity<TicketDto> createTicket(@Valid @RequestBody SelfServiceTicketDto ticketDto)
      throws URISyntaxException {
    TicketDto ticketDto1 = selfService.createTicket(ticketDto);

    return ResponseEntity.created(new URI("/api/ticket/" +
        ticketDto1.getId()))
        .body(ticketDto1);
  }

  @ApiOperation(value = "Modify a ticket")
  @PatchMapping(value = "selfservice/PATCH/{code}", consumes = "application/json-patch+json")
  public ResponseEntity<TicketDto> updateTicket(@PathVariable String code, @RequestBody @Valid
      TicketUpdateByUserDto ticketDto) {

    return new ResponseEntity<>(selfService.updateTicket(ticketDto,code), HttpStatus.NO_CONTENT);
  }

}
