package com.smart_home.Device.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.Factory.Interface.Delete.IDeviceDelete;
import com.smart_home.Device.Factory.Service.Delete.DeviceDeleteFactoryService;
import com.smart_home.Device.Model.Device;
import com.smart_home.Device.Singleton.DeviceSingleton;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class DeleteDeviceService {
    private final JwtService jwtService;
    private final DeviceSingleton deviceSingleton;
    private final DeviceDeleteFactoryService deviceDeleteFactoryService;


    public DeleteDeviceService(
            JwtService jwtService,
            DeviceSingleton deviceSingleton,
            DeviceDeleteFactoryService deviceDeleteFactoryService) {
        this.jwtService = jwtService;
        this.deviceSingleton = deviceSingleton;
        this.deviceDeleteFactoryService = deviceDeleteFactoryService;
    }

    public void deleteDevice(Long id, HttpServletRequest request) throws ClassNotFoundException {
        User user = jwtService.extractUser(request);
        Device device = deviceSingleton.getDevice(id, user);
        IDeviceDelete deleteFactory = deviceDeleteFactoryService.delete(device);
        deleteFactory.delete(device);
        deviceSingleton.deleteDevice(device);
    }
}
