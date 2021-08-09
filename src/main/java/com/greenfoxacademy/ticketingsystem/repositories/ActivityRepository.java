package com.greenfoxacademy.ticketingsystem.repositories;

import com.greenfoxacademy.ticketingsystem.models.Activity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {

  List<Activity> findByCode(String code);
  List<Activity> findAll();

}
