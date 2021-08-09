package com.greenfoxacademy.ticketingsystem.aspect;

import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.services.ActivityService;
import com.greenfoxacademy.ticketingsystem.services.NotificationService;
import com.greenfoxacademy.ticketingsystem.services.TicketService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TicketServiceAgentAspect {


  @Autowired
  private ActivityService activityService;

  @Autowired
  private NotificationService notificationService;

  @AfterReturning(
      pointcut = "execution(* com.greenfoxacademy.ticketingsystem.services.TicketService*.createTicket(..))",
      returning = "result")
  public void afterUpdateTicketAdvice(TicketDto result) {
    activityService.createActivityByAgent(result);
    notificationService.notifyUserAboutChanges(result);

  }


}

