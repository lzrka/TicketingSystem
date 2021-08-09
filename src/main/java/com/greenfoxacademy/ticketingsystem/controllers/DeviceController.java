package com.greenfoxacademy.ticketingsystem.controllers;

import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.dtos.DeviceDto;
import com.greenfoxacademy.ticketingsystem.services.DeviceService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeviceController {

  @Autowired
  DeviceService deviceService;

  @GetMapping("/devicesIndevice")
  @ApiOperation(value = "Lists all device")
  public ResponseEntity<List<Device>> listDevicesInDevice() {
    return ResponseEntity.ok(deviceService.getAllDeviceInDevice());
  }

  @GetMapping("/devices")
  @ApiOperation(value = "Lists all device")
  public ResponseEntity<List<DeviceDto>> listDevices() {
    return ResponseEntity.ok(deviceService.getAllDevice());
  }

  @GetMapping("device/{id}")
  @ApiOperation(value = "find an existing device")
  public ResponseEntity<DeviceDto> findUserById(@PathVariable Long id) {
    return new ResponseEntity<>(deviceService.getDeviceById(id),HttpStatus.OK);
  }


  @ApiOperation(value = "Create a new Device and assign it to User")
  @PostMapping("device/add")
  public ResponseEntity<DeviceDto> createDevice(@Valid @RequestBody DeviceDto deviceDto)
      throws URISyntaxException {

    DeviceDto savedDto = deviceService.createDevice(deviceDto);

    return ResponseEntity.created(new URI("/api/user/" +
        savedDto.getId()))
        .body(savedDto);

  }


  @ApiOperation(value = "Delete a device with an ID")
  @DeleteMapping("/device/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
    deviceService.removeDevice(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "Modify an existing Device")
  @PatchMapping(value = "device/patch/{id}", consumes = "application/json-patch+json")
  public ResponseEntity<DeviceDto> updateDevice(@PathVariable Long id,@RequestBody @Valid DeviceDto deviceDto) {

    return new ResponseEntity<>(deviceService.updateDevice(deviceDto,id), HttpStatus.NO_CONTENT);
  }



}


