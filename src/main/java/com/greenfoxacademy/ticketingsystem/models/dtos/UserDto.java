package com.greenfoxacademy.ticketingsystem.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long Id;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @Email
  private String email;


  public User convertToUser() {
      return new User().toBuilder().Id(this.Id).firstName(this.firstName)
          .lastName(this.lastName).email(this.email).build();

  }

}
