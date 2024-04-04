package com.smart_home.Aquarium.Service;

import com.smart_home.Aquarium.Singleton.AquariumSingleton;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Aquarium.DTO.DeviceAquariumDTO;
import com.smart_home.Device.DTO.DeviceDTO;
import com.smart_home.Aquarium.DTOMapper.DeviceAquariumDTOMapper;
import com.smart_home.Device.DTOMapper.DeviceDTOMapper;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Aquarium.Model.DeviceAquarium;
import com.smart_home.Device.Repository.DeviceRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceGetAquarium {
    private final DeviceRepository deviceRepository;
    private final DeviceAquariumDTOMapper deviceAquariumDTOMapper;
    private final JwtService jwtService;
    private final DeviceDTOMapper deviceDTOMapper;

    private final AquariumSingleton aquariumSingleton;

    public DeviceServiceGetAquarium(
            DeviceRepository deviceRepository,
            DeviceAquariumDTOMapper deviceAquariumDTOMapper,
            JwtService jwtService,
            DeviceDTOMapper deviceDTOMapper,
            AquariumSingleton aquariumSingleton) {
        this.deviceRepository = deviceRepository;
        this.deviceAquariumDTOMapper = deviceAquariumDTOMapper;
        this.jwtService = jwtService;
        this.deviceDTOMapper = deviceDTOMapper;
        this.aquariumSingleton = aquariumSingleton;
    }

    public List<DeviceDTO> getAll(HttpServletRequest request) {
        User user = jwtService.extractUser(request);
        return deviceRepository.findByUserAndType(user, DeviceType.AQUARIUM).stream()
                .map(deviceDTOMapper)
                .collect(Collectors.toList());
    }

    public DeviceAquariumDTO getSettings(HttpServletRequest request, Long id) {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = aquariumSingleton.getAquarium(id, user);
        return deviceAquariumDTOMapper.apply(aquarium);
    }
}
