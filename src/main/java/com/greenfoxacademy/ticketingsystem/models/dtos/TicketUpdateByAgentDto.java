package com.greenfoxacademy.ticketingsystem.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfoxacademy.ticketingsystem.models.Priority;
import com.greenfoxacademy.ticketingsystem.models.Status;
import com.greenfoxacademy.ticketingsystem.services.validator.EnumValidator;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateByAgentDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  private String description;

  private String title;
  @EnumValidator(
      enumClazz = Priority.class,
      message = "Priority options : Low, Medium, High")
  private String priority;

  @EnumValidator(
      enumClazz = Status.class,
      message = "Status options : Open, In_Progress, Pending, Closed")
  private String status;
  @NotBlank
  @Size(max = 2)
  private String assignee_id;

  private String solution;

  @ApiModelProperty(value = "update comment", dataType = "String", notes = "Must to be added for update")
  @NotBlank
  private String update;

}
