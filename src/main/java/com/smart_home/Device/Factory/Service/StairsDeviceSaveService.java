package com.smart_home.Device.Factory.Service;

import com.smart_home.Device.Repository.DeviceStairsRepository;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Factory.Interface.IDeviceSave;
import com.smart_home.Device.Model.DeviceStairs;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;

@Service
public class StairsDeviceSaveService implements IDeviceSave {

    private final DeviceStairsRepository deviceStairsRepository;

    public StairsDeviceSaveService(DeviceStairsRepository deviceStairsRepository) {
        this.deviceStairsRepository = deviceStairsRepository;
    }

    @Override
    public void save(User user, DatagramPacket deviceInfo, String name) {
        DeviceStairs stairs = new DeviceStairs();
        stairs.setIp(deviceInfo.getAddress());
        stairs.setPort(deviceInfo.getPort());
        stairs.setType(DeviceType.STAIRS);
        stairs.setUser(user);
        stairs.setName(name);
        deviceStairsRepository.save(stairs);
    }
}
