package com.greenfoxacademy.ticketingsystem.services;


import com.greenfoxacademy.ticketingsystem.exceptions.ResourceNotFoundException;
import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.repositories.TicketRepository;
import com.greenfoxacademy.ticketingsystem.repositories.UserRepository;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private UserRepository userRepository;

  public void notifyUserAboutChanges(TicketDto ticketDto) {
    Set<User> users =
        getTicketByCode(ticketDto.getCode()).getUsers();

User user = userRepository.findById(Long.parseLong(ticketDto.getUserId())).orElse(null);

    sendMail(user.getFirstName() + " " + user.getLastName(), "lzrka1992@gmail.com", ticketDto);

  }

  private void sendMail(String userName, String address, TicketDto ticketDto) {
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("1631e1f4f351fe");
    message.setTo(address);
    message.setSubject("Information about ticket changes");
    message.setText("Dear " + userName + ",\n"
        + "There was a change in your ticket " + ticketDto.getCode()  + " "  +
        ".\n\n" +
        ticketDto.getLastUpdate()
    );
    mailSender.send(message);
  }

  private Ticket getTicketByCode(String code) {
    return ticketRepository.findByCode(code)
        .orElseThrow(
            () -> new ResourceNotFoundException(
                "Ticket with code: " + code + " doesn't exist"));
  }


}
