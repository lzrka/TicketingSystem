package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.exceptions.ResourceNotFoundException;
import com.greenfoxacademy.ticketingsystem.models.Activity;
import com.greenfoxacademy.ticketingsystem.models.Agent;
import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.repositories.ActivityRepository;
import com.greenfoxacademy.ticketingsystem.repositories.AgentRepository;
import com.greenfoxacademy.ticketingsystem.repositories.TicketRepository;
import com.greenfoxacademy.ticketingsystem.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AgentRepository agentRepository;
  @Autowired
  private ActivityRepository activityRepository;

  public List<Activity>findAll(){ return activityRepository.findAll();}
  public List<Activity> findByCode(String code) {
    return activityRepository.findByCode(code);
  }

  public void createActivityByAgent(TicketDto result) {

    Agent agent = agentRepository.findById(result.getAssigne_id()).orElseThrow(
        () -> new ResourceNotFoundException("User not exist, Please provide an existing one!"));
    Ticket ticket = ticketRepository.findById(result.getId()).orElseThrow(
        () -> new ResourceNotFoundException("Ticket not exist, Please provide an existing one!"));
    Activity activity =
        new Activity().toBuilder().code(ticket.getCode()).modification("created new ticket")
            .modifiedBy(agent.getUsername()).build();
    activityRepository.save(activity);

  }

  public void createActivityByUser(TicketDto result) {
    User user = userRepository.findById(Long.parseLong(result.getUserId())).orElseThrow(
        () -> new ResourceNotFoundException("User not exist, Please provide an existing one!"));
    Ticket ticket = ticketRepository.findById(result.getId()).orElseThrow(
        () -> new ResourceNotFoundException("Ticket not exist, Please provide an existing one!"));
    Activity activity =
        new Activity().toBuilder().code(ticket.getCode()).modification("created new ticket")
            .modifiedBy(user.getFirstName().substring(0, 2) + user.getLastName() + "(" +
                user.getCompanyCode() + ")").build();
    activityRepository.save(activity);
  }

  public void updateActivityByUser(TicketDto result) {
    User user = userRepository.findById(Long.parseLong(result.getUserId())).orElseThrow(
        () -> new ResourceNotFoundException("User not exist, Please provide an existing one!"));
    Ticket ticket = ticketRepository.findById(result.getId()).orElseThrow(
        () -> new ResourceNotFoundException("Ticket not exist, Please provide an existing one!"));
    Activity activity = new Activity().toBuilder().code(ticket.getCode())
        .modification("description or/and title changed by user")
        .modifiedBy(
            user.getFirstName().substring(0, 2) + user.getLastName() + "(" + user.getCompanyCode() +
                ")").build();
    activityRepository.save(activity);
  }

  public void updateActivityByAgent(TicketDto result) {
    User user = userRepository.findById(Long.parseLong(result.getUserId())).orElseThrow(
        () -> new ResourceNotFoundException("User not exist, Please provide an existing one!"));
    Ticket ticket = ticketRepository.findById(result.getId()).orElseThrow(
        () -> new ResourceNotFoundException("Ticket not exist, Please provide an existing one!"));
    Activity activity = new Activity().toBuilder().code(ticket.getCode())
        .modification(result.getLastUpdate())
        .modifiedBy(
            user.getFirstName().substring(0, 2) + user.getLastName() + "(" + user.getCompanyCode() +
                ")").build();
    activityRepository.save(activity);
  }
}
