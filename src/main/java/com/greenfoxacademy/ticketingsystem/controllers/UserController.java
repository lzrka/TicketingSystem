package com.greenfoxacademy.ticketingsystem.controllers;


import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByAgentDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByUserDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import com.greenfoxacademy.ticketingsystem.services.DeviceService;
import com.greenfoxacademy.ticketingsystem.services.UserService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  UserService userService;
  @Autowired
  DeviceService deviceService;



  @GetMapping("/usersInuser")
  @ApiOperation(value = "Lists all users")
  public ResponseEntity<List<User>> listUsersInUser() {
    return ResponseEntity.ok(userService.getAllUserInUser());
  }

  @GetMapping("/users")
  @ApiOperation(value = "Lists all users")
  public ResponseEntity<List<UserDto>> listUsers() {
    return ResponseEntity.ok(userService.getAllUser());
  }

  @GetMapping("/users/{id}")
  @ApiOperation(value = "find an existing user")
  public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
  }
  
  @ApiOperation(value = "Add a new User")
  @PostMapping("/user/add")
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
      throws URISyntaxException {
    UserDto savedDto = userService.createUser(userDto);

    return ResponseEntity.created(new URI("/api/user/" +
        savedDto.getId()))
        .body(savedDto);
  }
  @ApiOperation(value = "Update an existing User with an ID")
  @PatchMapping("/user/patch/{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid
      UserDto userDto) {

    return new ResponseEntity<>(userService.updateUser(userDto,id), HttpStatus.NO_CONTENT);
  }


  @ApiOperation(value = "Delete a User with an ID")
  @DeleteMapping("/user/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.removeUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
