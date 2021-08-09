package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.models.Ticket;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByAgentDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.TicketUpdateByUserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTicketFromDto(TicketDto dto, @MappingTarget Ticket entity);
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTicketFromUpdatebyAgentDto(TicketUpdateByAgentDto dto, @MappingTarget Ticket entity);
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTicketFromUpdateByUserDto(TicketUpdateByUserDto dto, @MappingTarget Ticket entity);
}
