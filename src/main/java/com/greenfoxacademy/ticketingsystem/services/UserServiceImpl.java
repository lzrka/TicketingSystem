package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.exceptions.ResourceAlreadyExistsException;
import com.greenfoxacademy.ticketingsystem.exceptions.ResourceNotFoundException;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import com.greenfoxacademy.ticketingsystem.repositories.TicketRepository;
import com.greenfoxacademy.ticketingsystem.repositories.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  TicketRepository ticketRepository;
  @Autowired
  UserMapper userMapper;



  private boolean existsById(Long id) {
    return userRepository.existsById(id);
  }

  @Override
  public List<User> getAllUserInUser() {
    return userRepository.findAll();
  }

  @Override
  public List<UserDto> getAllUser() {
    return userRepository.findAll().stream().map(User::convertToDto).collect(Collectors.toList());
  }

  @Override
  public UserDto getUserById(Long id) {
    Objects.requireNonNull(id);
    User user = userRepository.findById(id).orElseThrow
        (() -> new ResourceNotFoundException("User with id: " + id + " doesn't exist"));
    return user.convertToDto();
  }

  @Override
  @Transactional
  public UserDto createUser(UserDto userdto) {
    Objects.requireNonNull(userdto);
    if (userdto.getId() != null && existsById(userdto.getId())) {
      throw new ResourceAlreadyExistsException(
          "User with id: " + userdto.getId() + " already exists");
    }
     User convertedUser = userdto.convertToUser();


      while (userRepository.existsByCompanyCode(convertedUser.getCompanyCode())) {
        convertedUser.setCompanyCode(convertedUser.generateCode());
      }

      userRepository.save(convertedUser);

      return convertedUser.convertToDto();
    }


  @Override
  @Transactional
  public UserDto updateUser(UserDto userDto,Long id) {
    Objects.requireNonNull(userDto);
    if (!existsById(id)) {
      throw new ResourceNotFoundException("User with id: " + userDto.getId() + " doesn't exist");
    } else {

      User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: "  + id +  " doesn't exist"));
      userMapper.updateUserFromDto(userDto,user);
      userRepository.save(user);
      return userDto;
    }
  }



  @Override
  @Transactional
  public void removeUser(Long id) {
    Objects.requireNonNull(id);
    if (!existsById(id)) {
      throw new ResourceNotFoundException("User with id: " + id + " doesn't exist ");
    }
    userRepository.deleteById(id);
  }



}
