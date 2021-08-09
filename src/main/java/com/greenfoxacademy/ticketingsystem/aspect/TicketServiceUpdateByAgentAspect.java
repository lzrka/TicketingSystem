package com.greenfoxacademy.ticketingsystem.aspect;

import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.services.ActivityService;
import com.greenfoxacademy.ticketingsystem.services.NotificationService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TicketServiceUpdateByAgentAspect {


  @Autowired
  private ActivityService activityService;

  @Autowired
  private NotificationService notificationService;

  @AfterReturning(
      pointcut = "execution(* com.greenfoxacademy.ticketingsystem.services.TicketService*.updateTicket(..))",
      returning = "result")
  public void afterUpdateTicketAdvice(TicketDto result) {
    activityService.updateActivityByAgent(result);
    notificationService.notifyUserAboutChanges(result);
  }
}
