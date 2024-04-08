package com.smart_home.Device.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.Model.Device;
import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Device.Singleton.DeviceSingleton;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeviceService {

    private final DeviceSingleton deviceSingleton;
    private final JwtService jwtService;

    public UpdateDeviceService(
            DeviceSingleton deviceSingleton,
            JwtService jwtService
    ) {
        this.deviceSingleton = deviceSingleton;
        this.jwtService = jwtService;
    }

    public void updateDevice(Long id, HttpServletRequest request, AddDeviceRequest form) {
        User user = jwtService.extractUser(request);
        Device device = deviceSingleton.getDevice(id, user);
        device.setName(form.getName());
        deviceSingleton.saveDevice(device);
    }
}
