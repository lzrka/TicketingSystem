package com.greenfoxacademy.ticketingsystem.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfoxacademy.ticketingsystem.models.Agent;
import com.greenfoxacademy.ticketingsystem.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long Id;

  private String firstName;

  private String lastName;


  public Agent convertToAgent() {
    return new Agent().toBuilder().Id(this.Id).firstName(this.firstName)
        .lastName(this.lastName).build();

  }

}
