package com.greenfoxacademy.ticketingsystem.controllers;

import com.greenfoxacademy.ticketingsystem.models.Activity;
import com.greenfoxacademy.ticketingsystem.models.Agent;
import com.greenfoxacademy.ticketingsystem.services.ActivityService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ActivityController {

  @Autowired
  private ActivityService activityService;

  @GetMapping("/activity/{code}")
  @ApiOperation(value = "Find the activity of a ticket")
  public ResponseEntity<List<Activity>> listAllAgent(@PathVariable String code) {
    return ResponseEntity.ok(activityService.findByCode(code));
  }
  @GetMapping("/activities")
  @ApiOperation(value = "Find all activities")
  public ResponseEntity<List<Activity>> listAllAgent() {
    return ResponseEntity.ok(activityService.findAll());
  }

}
