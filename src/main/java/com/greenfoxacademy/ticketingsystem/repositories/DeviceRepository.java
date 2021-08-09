package com.greenfoxacademy.ticketingsystem.repositories;

import com.greenfoxacademy.ticketingsystem.models.Device;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
  List<Device> findAll();
  boolean existsByCode(String companycode);
}
