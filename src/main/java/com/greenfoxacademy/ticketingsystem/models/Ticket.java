package com.greenfoxacademy.ticketingsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@Table(name="ticket")
@AllArgsConstructor
@SuppressWarnings("JpaDataSourceORMInspection")
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false,updatable = false)
  private Long id;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private String code = generateCode();
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Priority priority;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status = Status.Open;
  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();
  @Column(nullable = false)
  private LocalDateTime upDatedAt = LocalDateTime.now();
  @Column(nullable = false)
  private String solution = "";

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "tickets")
  Set<User>users = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assigne_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonBackReference
  private Agent assigne;

  public String generateCode() {
    String code = "T";
    Random r = new Random();
    int numbers = 100000 + (int)(r.nextFloat() * 899900);
    code += String.valueOf(numbers);
    return code;
  }

  public void addUser(User user) {
    users.add(user);
    user.getTickets().add(this);
  }


  public TicketDto convertToDto() {
    if (assigne != null)
    return new TicketDto(id,code,listConverter(),title,priority.toString(),description,assigne.getId(),null);
    else
      return new TicketDto(id,code,listConverter(),title,priority.toString(),description,null,null);
  }

  @PrePersist
  void createdAt() {
    createdAt = LocalDateTime.now();

    System.out.println("létrehoztam a ticketet");
  }
  @PreUpdate
  void updatedAt() {
    upDatedAt = LocalDateTime.now();
  }

  public String listConverter() {
    String empty = "";
    return users.stream()
        .map(x -> x.getId().toString())
        .collect(Collectors.joining(empty));
  }

}
