package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.DeviceDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByUserDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import java.util.List;

public interface UserService {

  List<User>getAllUserInUser();

  List<UserDto> getAllUser();

  UserDto getUserById(Long id);

  UserDto createUser(UserDto userDto);

  UserDto updateUser(UserDto userDto, Long id);

  void removeUser(Long id);


}

