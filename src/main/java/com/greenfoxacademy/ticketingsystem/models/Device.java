package com.greenfoxacademy.ticketingsystem.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.greenfoxacademy.ticketingsystem.models.dtos.DeviceDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="device")
@Builder(toBuilder = true)
@SuppressWarnings("JpaDataSourceORMInspection")
@AllArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")

public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false,updatable = false)
  private Long Id;
  @Column(nullable = false)
  private String code = generateCode();
  @Column(nullable = false)
  @NotNull
  private String model;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  private DeviceType type ;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonBackReference
  private User user;

  public String generateCode() {
    String code = "";
    Random r = new Random();
    int numbers = 100000 + (int)(r.nextFloat() * 899900);
    code += String.valueOf(numbers);
    return code;
  }

  public DeviceDto convertToDto() {
    if (!Objects.isNull(user))
    return new DeviceDto(this.Id, this.type.name(),user.getId(),this.model);
    else
      return new DeviceDto(this.Id, this.type.name(),null,this.model);
  }

}
