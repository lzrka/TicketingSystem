package com.greenfoxacademy.ticketingsystem.repositories;

import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  List<User>findAll();
  boolean existsByCompanyCode(String companycode);
  User findUserByDevices(Device device);
}
