package com.smart_home.Device.DTOMapper;

import com.smart_home.Device.DTO.DeviceDTO;
import com.smart_home.Device.Model.Device;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DeviceDTOMapper implements Function <Device, DeviceDTO> {
    @Override
    public DeviceDTO apply(Device device) {
        return new DeviceDTO(device.getName(), device.getId(), device.getType().name());
    }
}
