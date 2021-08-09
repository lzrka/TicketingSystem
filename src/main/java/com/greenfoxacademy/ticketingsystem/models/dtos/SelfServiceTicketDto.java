package com.greenfoxacademy.ticketingsystem.models.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

import com.greenfoxacademy.ticketingsystem.models.Priority;
import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.services.validator.EnumValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelfServiceTicketDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;
  @NotBlank
  @Size(max = 2)
  private String userId;
  @NotBlank
  private String title;
  @EnumValidator(
      enumClazz = Priority.class,
      message = "Priority options : Low, Medium, High"
  )
  private String priority;
  @NotBlank
  private String description;


  public Ticket convertToTicket(User user) {
    Ticket ticket = new Ticket().toBuilder().id(id).title(title).description(description).priority(
        Priority.valueOf(priority)).build();
    user.addTicket(ticket);
    ticket.addUser(user);

    return ticket;
  }
}
