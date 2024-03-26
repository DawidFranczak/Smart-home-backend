package com.smart_home.Device.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Model.Device;
import com.smart_home.Device.Model.DeviceAquarium;
import com.smart_home.Device.Model.DeviceSettings;
import com.smart_home.Device.Model.DeviceStairs;
import com.smart_home.Device.Repository.DeviceAquariumRepository;
import com.smart_home.Device.Repository.DeviceSettingsRepository;
import com.smart_home.Device.Repository.DeviceStairsRepository;
import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.UDP.UDP;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.util.Optional;

@Service
public class DeviceServiceAddNewDevice {

    private final JwtService jwtService;
    private final UDP udp;
    private final DeviceSettingsRepository deviceSettingsRepository;
    private final DeviceAquariumRepository deviceAquariumRepository;
    private final DeviceStairsRepository deviceStairsRepository;

    public DeviceServiceAddNewDevice(
            JwtService jwtService,
            UDP udp,
            DeviceSettingsRepository deviceSettingsRepository,
            DeviceAquariumRepository deviceAquariumRepository,
            DeviceStairsRepository deviceStairsRepository
    ) {

        this.jwtService = jwtService;
        this.udp = udp;
        this.deviceSettingsRepository = deviceSettingsRepository;
        this.deviceAquariumRepository = deviceAquariumRepository;
        this.deviceStairsRepository = deviceStairsRepository;
    }

    public void addNewDevice(HttpServletRequest request, AddDeviceRequest form) throws UnknownHostException {
        User user = jwtService.extractUser(request);
        DeviceType deviceType = getDeviceType(form.getType());
        DatagramPacket deviceInfo = findDeviceInLocalNetwork(deviceType);
        saveDevice(user, deviceType, deviceInfo, form.getName());
    }

    private void saveDevice(User user, DeviceType deviceType, DatagramPacket deviceInfo, String name) {
        switch (deviceType) {
            case AQUARIUM -> {
                Device device = setDeviceAttributes(new DeviceAquarium(), user, deviceType, deviceInfo, name);
                deviceAquariumRepository.save((DeviceAquarium) device);
            }
            case STAIRS -> {
                Device device = setDeviceAttributes(new DeviceStairs(), user, deviceType, deviceInfo, name);
                deviceStairsRepository.save((DeviceStairs) device);
            }
        }
        throw new RuntimeException();
    }

    private Device setDeviceAttributes(Device device, User user, DeviceType deviceType, DatagramPacket deviceInfo, String name) {
        device.setIp(deviceInfo.getAddress());
        device.setName(name);
        device.setType(deviceType);
        device.setPort(deviceInfo.getPort());
        device.setUser(user);
        return device;
    }
    private DatagramPacket findDeviceInLocalNetwork(DeviceType deviceType) {
        int devicePort = getDevicePort(deviceType);
        String deviceAddingPassword = getDeviceAddingPassword(deviceType);
        return udp.scanLocalHost(deviceAddingPassword, devicePort, 40, "Device not found.");
    }

    private String getDeviceAddingPassword(DeviceType type) {
        Optional<DeviceSettings> optional = deviceSettingsRepository.findByKey(type.name().toLowerCase() + "AddingPassword");
        if(optional.isEmpty())
            throw new RuntimeException("Optional is empty check DB!");
        return optional.get().getValue();
    }

    private int getDevicePort(DeviceType type) {
        Optional<DeviceSettings> optional = deviceSettingsRepository.findByKey(type.name().toLowerCase() + "Port");
        if(optional.isEmpty())
            throw new RuntimeException("Optional is empty check DB!");

        String stringPort = optional.get().getValue();
        try{
            return Integer.parseInt(stringPort);

        }catch (NumberFormatException e){
            throw new RuntimeException(e);
        }
    }
    private DeviceType getDeviceType(String type) {
        for (DeviceType deviceType : DeviceType.values()) {
            if (deviceType.name().equals(type.toUpperCase()))
                return deviceType;
        }
        throw new RuntimeException("Device with this type doesn't exists.");
    }

}
