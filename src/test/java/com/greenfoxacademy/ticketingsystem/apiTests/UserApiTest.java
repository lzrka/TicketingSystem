package com.greenfoxacademy.ticketingsystem.apiTests;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.ticketingsystem.exceptions.ResourceNotFoundException;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import com.greenfoxacademy.ticketingsystem.services.UserService;
import java.nio.charset.Charset;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  @Test
  @DisplayName("Test GET 'api/accounts' is ok")
  public void getsAllUsers() throws Exception {
    User user0 = new User();
    user0.setId(0L);
    User user1 = new User();
    user1.setId(1L);

    Mockito.when(
        userService.getAllUser().stream().map(UserDto::convertToUser).collect(Collectors.toList()))
        .thenReturn(Lists.newArrayList(user0, user1));

    mockMvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(0)))
        .andExpect(jsonPath("$[1].id", is(1)));

  }

  @Test
  @DisplayName("Test POST 'api/user' 201 CREATED")
  public void createNewUserIsCreated() throws Exception {
    User user0 = new User().toBuilder().Id(1L).email("teszt@teszt.hu").firstName("Nagy")
        .lastName("József").devices(null).tickets(null).build();


    Mockito.when(userService.createUser(Mockito.any(UserDto.class)))
        .thenReturn(user0.convertToDto());

    mockMvc.perform(post("/api/user/add")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content("{ \"email\": \"teszt@teszt.hu\", \"firstName\": \"Nagy\", \"lastName\": \"Károly\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.email", is("teszt@teszt.hu")))
        .andExpect(jsonPath("$.devices").doesNotExist())
        .andExpect(jsonPath("$.tickets").doesNotExist());


  }
  @Test
  @DisplayName("Test PATCH 'user/patch/{id}' 201 CREATED")
  public void updateUserWhenDtoValid() throws Exception {
    UserDto userDto = new UserDto();

    Mockito.when(userService.updateUser(userDto, 1L)).thenReturn(userDto);

    mockMvc.perform(patch("/api/user/patch/1")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content("{ \"email\": \"bettinateszt@teszt.hu\", \"firstName\": \"Kis\", \"lastName\": \"Bettina\"}"))
        .andExpect(status().isNoContent());



  }

  @Test
  @DisplayName("Test DELETE 'api/user/0' 404 NOT FOUND")
  public void deleteUserFails() throws Exception {
    UserDto user = new UserDto();
    user.setId(0L);

    Mockito.doThrow(new ResourceNotFoundException(
        "User with id: " + user.getId() + " doesn't exist"))
        .when(userService).removeUser(user.getId());

    mockMvc.perform(delete("/api/user/0"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(org.hamcrest.Matchers
            .containsString("User with id: 0 doesn't exist")));
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }
}
