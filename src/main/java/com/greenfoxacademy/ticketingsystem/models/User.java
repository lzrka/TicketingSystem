package com.greenfoxacademy.ticketingsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "user")
@SuperBuilder(toBuilder = true)
@SuppressWarnings("JpaDataSourceORMInspection")
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long Id;
  @Column(nullable = false)
  @NotBlank
  private String firstName;
  @Column(nullable = false)
  @NotBlank
  private String lastName;
  @Column(nullable = false)
  private String companyCode = generateCode();
  @Column(nullable = false)
  @Email
  @NotBlank
  private String email;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Device> devices = new HashSet<Device>();

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  @JoinTable(name = "user_ticket",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "ticket_id")})
  private Set<Ticket> tickets = new HashSet<Ticket>();


  public String generateCode() {
    String code = "";
    Random r = new Random();
    int numbers = 100000 + (int) (r.nextFloat() * 899900);
    code += String.valueOf(numbers);
    return code;
  }

  public UserDto convertToDto() {
    return new UserDto(this.Id, this.firstName, this.lastName, this.email);
  }


  public void addDevice(Device device) {
    devices.add(device);
    device.setUser(this);
  }

  public void addTicket(Ticket ticket) {
    tickets.add(ticket);
    ticket.users.add(this);

  }

  public void removeDevice(Device device) {
    devices.stream().filter(x -> !x.getCode().equals(device.getCode())).collect(Collectors.toSet());


  }
}
