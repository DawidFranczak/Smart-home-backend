package com.smart_home.Device.DTOMapper;

import com.smart_home.Device.DTO.DeviceAquariumDTO;
import com.smart_home.Device.Model.DeviceAquarium;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class DeviceAquariumDTOMapper implements Function<DeviceAquarium, DeviceAquariumDTO> {
    @Override
    public DeviceAquariumDTO apply(DeviceAquarium deviceAquarium) {

        return new DeviceAquariumDTO(
                deviceAquarium.getId(),
                deviceAquarium.getName(),
                deviceAquarium.getColor(),
                deviceAquarium.getLedStart(),
                deviceAquarium.getLedStop(),
                deviceAquarium.getFluoStart(),
                deviceAquarium.getFluoStop(),
                deviceAquarium.isMode(),
                deviceAquarium.isLedMode(),
                deviceAquarium.isLedMode()
        );
    }
}
