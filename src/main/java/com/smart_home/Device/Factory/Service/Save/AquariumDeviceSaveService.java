package com.smart_home.Device.Factory.Service.Save;

import com.smart_home.Aquarium.Model.DeviceAquarium;
import com.smart_home.Aquarium.Singleton.AquariumSingleton;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Factory.Interface.Save.IDeviceSave;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;

@Service
public class AquariumDeviceSaveService implements IDeviceSave {

    private final AquariumSingleton aquariumSingleton;

    public AquariumDeviceSaveService(AquariumSingleton aquariumSingleton) {
        this.aquariumSingleton = aquariumSingleton;
    }

    @Override
    public void save(User user, DatagramPacket deviceInfo, String name) {
        DeviceAquarium aquarium = new DeviceAquarium();
        aquarium.setIp(deviceInfo.getAddress());
        aquarium.setPort(deviceInfo.getPort());
        aquarium.setName(name);
        aquarium.setType(DeviceType.AQUARIUM);
        aquarium.setUser(user);

        aquariumSingleton.save(aquarium);
    }
}
