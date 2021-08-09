package com.greenfoxacademy.ticketingsystem.repositories;

import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket,Long> {

  List<Ticket> findAll();

  boolean existsByCode(String ticketCode);

  Optional<Ticket> findByCode(String code);


}
