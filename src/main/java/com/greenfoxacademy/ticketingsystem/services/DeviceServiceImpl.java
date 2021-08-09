package com.greenfoxacademy.ticketingsystem.services;

import com.greenfoxacademy.ticketingsystem.exceptions.ResourceAlreadyExistsException;
import com.greenfoxacademy.ticketingsystem.exceptions.ResourceNotFoundException;
import com.greenfoxacademy.ticketingsystem.models.Device;
import com.greenfoxacademy.ticketingsystem.models.User;
import com.greenfoxacademy.ticketingsystem.models.dtos.DeviceDto;
import com.greenfoxacademy.ticketingsystem.models.dtos.UserDto;
import com.greenfoxacademy.ticketingsystem.repositories.DeviceRepository;
import com.greenfoxacademy.ticketingsystem.repositories.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

  @Autowired
  DeviceRepository deviceRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  DeviceMapper mapper;

  private boolean existsById(Long id) {
    return deviceRepository.existsById(id);
  }


  @Override
  public List<Device> getAllDeviceInDevice() {
    return deviceRepository.findAll();
  }

  @Override
  public List<DeviceDto> getAllDevice() {
    return deviceRepository.findAll().stream().map(Device::convertToDto).collect(Collectors.toList());
  }

  @Override
  public DeviceDto getDeviceById(Long id) {
   Objects.requireNonNull(id);
   Device device = deviceRepository.findById(id)
       .orElseThrow(() -> new ResourceNotFoundException("Device with id: " + id + " doesn't exist"));

   return device.convertToDto();
  }



  @Override
  public DeviceDto createDevice(DeviceDto deviceDto) {
    Objects.requireNonNull(deviceDto);
    if (deviceDto.getId() != null && existsById(deviceDto.getId()))
      throw new ResourceAlreadyExistsException(
          "Device with id: " + deviceDto.getId() + " already exists");

    Device convertedDevice = new Device();

    User user = new User();
    if (deviceDto.getUserId() != null) {
      user = userRepository.findById(deviceDto.getUserId())
          .orElseThrow(() -> new EntityNotFoundException(
              "Not possible to create Device, no existing User to connect with"));
      convertedDevice = deviceDto.convertToDevice(user);

    }
    else {
      convertedDevice = deviceDto.convertToDevice();
    }




      while (deviceRepository.existsByCode(convertedDevice.getCode())) {
        convertedDevice.setCode(convertedDevice.generateCode());
      }

      deviceRepository.save(convertedDevice);

      return convertedDevice.convertToDto();
    }




  @Override
  public DeviceDto updateDevice(DeviceDto deviceDto,Long deviceToModifyId) {
    Objects.requireNonNull(deviceDto);

    Device previousDevice = deviceRepository.findById(deviceToModifyId).orElseThrow( () -> new ResourceNotFoundException(
        "device not exist"));
    User previousUser = previousDevice.getUser();
    previousUser.removeDevice(previousDevice);
    userRepository.save(previousUser);


    User user = userRepository.findById(deviceDto.getUserId()).orElseThrow( () -> new ResourceNotFoundException(
        "Not possible to modify Device, no existing User to connect with"));

    if (!existsById(deviceDto.getId())) {
      throw new ResourceNotFoundException("Device with id: " + deviceDto.getId() + " doesn't exist");
    } else {

      mapper.updateDeviceFromDto(deviceDto,previousDevice );

      deviceRepository.save(previousDevice);
      return deviceDto;
    }
  }

  @Override
  public void removeDevice(Long id) {
    Objects.requireNonNull(id);
    if (!existsById(id)) {
      throw new ResourceNotFoundException("Device with id: " + id + " doesn't exist ");
    }

    deviceRepository.deleteById(id);
  }

  private static void codeModify(Device device) {
    if (!device.getType().toString().contains("[a-zA-Z]+")) {
      if (device.getType().toString().equals("desktop")) device.setCode("D" + device.getCode());
      if (device.getType().toString().equals("monitor")) device.setCode("M" + device.getCode());
      if (device.getType().toString().equals("notebook")) device.setCode("N" + device.getCode());
      if (device.getType().toString().equals("phone")) device.setCode("P" + device.getCode());
    }
  }

  public static boolean typeCheck(Device device) {
    if(!device.getType().toString().equals("desktop") || !device.getType().toString().equals("monitor")
        || !device.getType().toString().equals("notebook") || !device.getType().toString().equals("phone"))
        return false;

    return true;
  }


}


