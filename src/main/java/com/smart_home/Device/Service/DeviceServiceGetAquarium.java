package com.smart_home.Device.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.DTO.DeviceAquariumDTO;
import com.smart_home.Device.DTO.DeviceDTO;
import com.smart_home.Device.DTOMapper.DeviceAquariumDTOMapper;
import com.smart_home.Device.DTOMapper.DeviceDTOMapper;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Model.DeviceAquarium;
import com.smart_home.Device.Repository.DeviceAquariumRepository;
import com.smart_home.Device.Repository.DeviceRepository;
import com.smart_home.Exception.NotFound404Exception;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceGetAquarium {
    private final DeviceRepository deviceRepository;
    private final DeviceAquariumRepository deviceAquariumRepository;
    private final DeviceAquariumDTOMapper deviceAquariumDTOMapper;
    private final JwtService jwtService;
    private final DeviceDTOMapper deviceDTOMapper;

    public DeviceServiceGetAquarium(
            DeviceRepository deviceRepository,
            DeviceAquariumRepository deviceAquariumRepository,
            DeviceAquariumDTOMapper deviceAquariumDTOMapper,
            JwtService jwtService,
            DeviceDTOMapper deviceDTOMapper
    ) {
        this.deviceRepository = deviceRepository;
        this.deviceAquariumRepository = deviceAquariumRepository;
        this.deviceAquariumDTOMapper = deviceAquariumDTOMapper;
        this.jwtService = jwtService;
        this.deviceDTOMapper = deviceDTOMapper;
    }

    public List<DeviceDTO> getAll(HttpServletRequest request) {
        User user = jwtService.extractUser(request);
        return deviceRepository.findByUserAndType(user, DeviceType.AQUARIUM).stream()
                .map(deviceDTOMapper)
                .collect(Collectors.toList());
    }

    public DeviceAquariumDTO getSettings(HttpServletRequest request, Long id) {
        User user = jwtService.extractUser(request);
        Optional<DeviceAquarium> optional = deviceAquariumRepository.findAquariumByUserAndId(user, id);
        if(optional.isEmpty())
            throw new NotFound404Exception("Aquarium not found.");
        return deviceAquariumDTOMapper.apply(optional.get());
    }
}
