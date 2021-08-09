package com.greenfoxacademy.ticketingsystem.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.greenfoxacademy.ticketingsystem.models.dtos.AgentDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import java.util.Random;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="agent")
@Builder(toBuilder = true)
@SuppressWarnings("JpaDataSourceORMInspection")
@AllArgsConstructor
public class Agent{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false,updatable = false)
  private Long Id;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false)
  private String username;
  @OneToMany(mappedBy = "assigne", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonManagedReference
  Set<Ticket>assigned;

@PrePersist
  private void generateUsername() {
    Random random = new Random();
  username = getFirstName().substring(0,2) + getLastName() + random.nextInt(1000);
     }

  public AgentDto convertToAgentDto() {
   return new AgentDto(this.getId(),this.getFirstName(),this.getLastName());
  }


  public void addTicket(Ticket ticket) {
      assigned.add(ticket);
    ticket.setAssigne(this);

  }

  public void removeTicket(Ticket ticket){
    assigned.removeIf(x -> x.getCode().equals(ticket.getCode()));
  }

}
