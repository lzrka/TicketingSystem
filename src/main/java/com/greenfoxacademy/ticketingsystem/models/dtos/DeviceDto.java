package com.greenfoxacademy.ticketingsystem.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.DeviceType;
import com.greenfoxacademy.ticketingsystem.models.Priority;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.services.validator.EnumValidator;
import java.lang.reflect.Type;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long Id;
  @NotBlank
  @EnumValidator(
      enumClazz = DeviceType.class,
      message = "Devicetype options : notebook, desktop, phone, monitor"
  )
  private String type;

  private Long userId;
  @NotBlank
  private String model;

  public Device convertToDevice(User user) {
    Device device = new Device().toBuilder().Id(this.Id)
        .type(DeviceType.valueOf(type)).user(user).model(model).build();

    if (this.userId != null) {
      user.addDevice(device);
      device.setUser(user);
    } else
      device.setUser(null);
    return device;
  }

    public Device convertToDevice() {
      Device device = new Device().toBuilder().Id(this.Id)
          .type(DeviceType.valueOf(type)).user(null).model(model).build();
      return device;
  }





}
