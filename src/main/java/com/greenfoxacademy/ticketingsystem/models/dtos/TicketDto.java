package com.greenfoxacademy.ticketingsystem.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfoxacademy.ticketingsystem.models.Agent;
import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.DeviceType;
import com.greenfoxacademy.ticketingsystem.models.Priority;
import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.services.validator.EnumValidator;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String code;
  @NotBlank
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
  @NotNull
  private Long assigne_id;

  @JsonIgnore
  private String lastUpdate;




  public Ticket convertToTicket(Agent agent, User user) {
  Ticket ticket = new Ticket().toBuilder().id(id).title(title).description(description).priority(
      Priority.valueOf(priority)).build();

  user.addTicket(ticket);
  ticket.addUser(user);
  if(agent != null) {
    ticket.setAssigne(agent);
    agent.addTicket(ticket);
  }
      return ticket;
    }
  }


