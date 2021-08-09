package com.greenfoxacademy.ticketingsystem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.greenfoxacademy.ticketingsystem.exceptions.ResourceAlreadyExistsException;
import com.greenfoxacademy.ticketingsystem.exceptions.ResourceNotFoundException;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import com.greenfoxacademy.ticketingsystem.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
class UserServiceTest {


  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void getAllUsers() {
    List<User> testList = new ArrayList<>();

    User user1 = new User().toBuilder().Id(1L).lastName("Kovács").firstName("István").email("koistván@gmail.com").build();
    User user2 = new User().toBuilder().Id(2L).lastName("Nagy").firstName("Károly").email("nakároly@gmail.com").build();


    testList.add(user1);
    testList.add(user2);

    when(userRepository.findAll()).thenReturn(testList);

    List<User> expect = userService.getAllUser().stream().map(UserDto::convertToUser).collect(
        Collectors.toList());

    assertEquals(2, expect.size());
    verify(userRepository, times(1)).findAll();

  }


  @Test
  public void getUserByIdTestSuccess() {
    User user0 = new User().toBuilder().lastName("Károly")
        .firstName("Robert").Id(1L).email("vmi@asdas").companyCode("123456").devices(null).build();

    when(userRepository.findById(1L)).thenReturn(Optional.of(user0));

    User user = userService.getUserById(1L).convertToUser();

    assertEquals("123456", user0.getCompanyCode());
    assertEquals(null, user0.getDevices());
    assertEquals(1L, user.getId());
    assertEquals("vmi@asdas", user.getEmail());
  }

  @Test
  public void getUserByIdTestDoesntExist() {
    Exception exception =
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    assertEquals("User with id: 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void createUserTestSuccess() {
    User user0 = new User().toBuilder().lastName("Károly")
        .firstName("Robert").Id(1L).email("vmi@asdas").build();


    when(userRepository.save(user0)).thenReturn(user0);

    User user = userService.createUser(user0.convertToDto()).convertToUser();


    assertEquals("vmi@asdas", user.getEmail());
    assertEquals("Robert", user.getFirstName());
    assertEquals("Károly", user.getLastName());
    assertEquals(Collections.emptySet(), user.getDevices());
    assertEquals(6, user.getCompanyCode().length());

  }

  @Test
  public void createUserTestAlreadyExists() {
    User user1 = User.builder().Id(1L).build();
    UserDto userDto = user1.convertToDto();

    when(userRepository.existsById(user1.getId())).thenReturn(true);


    Exception exception = Assertions
        .assertThrows(ResourceAlreadyExistsException.class, () -> userService.createUser(userDto));

    assertEquals("User with id: 1 already exists", exception.getMessage());
  }

  @Test
  public void patchUserTestSuccess() {


    User user0 = new User().toBuilder()
        .Id(1L)
        .companyCode("123456")
        .lastName("Nagy")
        .firstName("József")
        .email("teszt@teszt.hu")
        .build();

    UserDto userDto = new UserDto(user0.getId(),"Kis","Károly","teszt@teszt.hu");

    when(userRepository.existsById(user0.getId())).thenReturn(true);
    when(userRepository.save(user0)).thenReturn(user0);





  }

  @Test
  public void updateUserTestDoesntExist() {


    User user0 = new User().toBuilder().Id(0L)
        .companyCode("123456")
        .lastName("Nagy")
        .firstName("József")
        .email("teszt@teszt.hu")
        .build();

    when(userRepository.existsById(user0.getId())).thenReturn(false);

    Exception exception = Assertions.assertThrows(ResourceNotFoundException.class,
        () -> userService.updateUser(user0.convertToDto(),0L));
    assertEquals("User with id: " + user0.getId() + " doesn't exist", exception.getMessage());
  }
  @Test
  public void removeUserTestSuccess() {
    when(userRepository.existsById(1L)).thenReturn(true);
    userService.removeUser(1L);
    verify(userRepository, times(1)).deleteById(1L);
  }

  @Test
  public void removeUserTestDoesntExist() {
    when(userRepository.existsById(1L)).thenReturn(false);
    Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.removeUser(1L));
    assertEquals("User with id: " + "1" + " doesn't exist ", exception.getMessage());
  }


}



