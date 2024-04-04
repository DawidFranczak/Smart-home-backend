package com.smart_home.Device.Singleton;

import com.smart_home.Device.Repository.DeviceSettingsRepository;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Model.DeviceSettings;
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


    private DeviceSingleton(
            UDP udp,
            DeviceSettingsRepository deviceSettingsRepository
    ){
        this.udp = udp;
        this.deviceSettingsRepository = deviceSettingsRepository;
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
