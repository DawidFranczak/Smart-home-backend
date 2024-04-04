package com.smart_home.Device.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Factory.Interface.IDeviceSave;
import com.smart_home.Device.Factory.Service.DeviceFactoryService;
import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Device.Singleton.DeviceSingleton;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;

@Service
public class AddNewDeviceService {

    private final JwtService jwtService;
    private final DeviceFactoryService deviceFactoryService;
    private final DeviceSingleton deviceSingleton;

    public AddNewDeviceService(
            JwtService jwtService,
            DeviceFactoryService deviceFactoryService,
            DeviceSingleton deviceSingleton
    ) {

        this.jwtService = jwtService;
        this.deviceFactoryService = deviceFactoryService;
        this.deviceSingleton = deviceSingleton;
    }

    public void addNewDevice(HttpServletRequest request, AddDeviceRequest form) throws ClassNotFoundException {
        User user = jwtService.extractUser(request);
        DeviceType deviceType = deviceSingleton.getDeviceType(form.getType());
        DatagramPacket deviceInfo = deviceSingleton.findDeviceInLocalNetwork(deviceType);
        IDeviceSave deviceService = deviceFactoryService.create(deviceType);
        deviceService.save(user, deviceInfo, form.getName());
    }
}
