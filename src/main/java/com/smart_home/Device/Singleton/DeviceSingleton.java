package com.smart_home.Device.Singleton;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Device.Factory.Service.Delete.DeviceDeleteFactoryService;
import com.smart_home.Device.Model.Device;
import com.smart_home.Device.Repository.DeviceRepository;
import com.smart_home.Device.Repository.DeviceSettingsRepository;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Model.DeviceSettings;
import com.smart_home.Exception.NotFound404Exception;
import com.smart_home.UDP.UDP;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.util.Optional;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service
public class DeviceSingleton {

    private final UDP udp;
    private final DeviceSettingsRepository deviceSettingsRepository;
    private final DeviceRepository deviceRepository;

    private DeviceSingleton(
            UDP udp,
            DeviceSettingsRepository deviceSettingsRepository,
            DeviceRepository deviceRepository, DeviceDeleteFactoryService deviceDeleteFactoryService){
        this.udp = udp;
        this.deviceSettingsRepository = deviceSettingsRepository;
        this.deviceRepository = deviceRepository;
    }

    public DatagramPacket findDeviceInLocalNetwork(DeviceType deviceType) {
        int devicePort = getDevicePort(deviceType);
        String deviceAddingPassword = getDeviceAddingPassword(deviceType);
        return udp.scanLocalHost(deviceAddingPassword, devicePort, 40, "Device not found.");
    }

    public DeviceType getDeviceType(String type) {
        for (DeviceType deviceType : DeviceType.values()) {
            if (deviceType.name().equals(type.toUpperCase()))
                return deviceType;
        }
        throw new RuntimeException("Device with this type doesn't exists.");
    }

    public Device getDevice(Long id, User user){
        Optional<Device> optional = deviceRepository.findByIdAndUser(id, user);
        if(optional.isEmpty())
            throw new NotFound404Exception("Device not found.");
        return optional.get();
    }

    public void saveDevice(Device device){
        deviceRepository.save(device);
    }

    public void deleteDevice(Device device) {
        deviceRepository.delete(device);
    }

    private String getDeviceAddingPassword(DeviceType type) {
        Optional<DeviceSettings> optional = deviceSettingsRepository.findBySettingsKey(type.name().toLowerCase() + "AddingPassword");
        return getSettingValue(optional);
    }

    private int getDevicePort(DeviceType type) {
        Optional<DeviceSettings> optional = deviceSettingsRepository.findBySettingsKey(type.name().toLowerCase() + "Port");
        String stringPort = getSettingValue(optional);

        try{
            return Integer.parseInt(stringPort);

        }catch (NumberFormatException e){
            throw new RuntimeException(e);
        }
    }

    private String getSettingValue(Optional<DeviceSettings> optional){
        if(optional.isEmpty())
            throw new RuntimeException("Optional is empty check DB!");
        return  optional.get().getSettingValue();
    }
}
