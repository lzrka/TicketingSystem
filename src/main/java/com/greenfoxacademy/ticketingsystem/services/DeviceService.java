package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.DeviceDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import java.util.List;


public interface DeviceService {

    List<Device>getAllDeviceInDevice();

    List<DeviceDto> getAllDevice();

    DeviceDto getDeviceById(Long id);

    DeviceDto createDevice(DeviceDto deviceDto);

    DeviceDto updateDevice(DeviceDto deviceDto,Long deviceToModifyId);

    void removeDevice(Long id);

  }


