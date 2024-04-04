package com.smart_home.Device.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.DTO.DeviceDTO;
import com.smart_home.Device.DTOMapper.DeviceDTOMapper;
import com.smart_home.Device.Repository.DeviceRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetDeviceService {

    private final JwtService jwtService;
    private final DeviceRepository deviceRepository;
    private final DeviceDTOMapper deviceDTOMapper;

    public GetDeviceService(
            JwtService jwtService,
            DeviceRepository deviceRepository,
            DeviceDTOMapper deviceDTOMapper) {
        this.jwtService = jwtService;
        this.deviceRepository = deviceRepository;
        this.deviceDTOMapper = deviceDTOMapper;
    }

    public List<DeviceDTO> getAll(HttpServletRequest request) {
        User user = jwtService.extractUser(request);
        return deviceRepository.findByUser(user).stream()
                .map(deviceDTOMapper)
                .collect(Collectors.toList());
    }
}
