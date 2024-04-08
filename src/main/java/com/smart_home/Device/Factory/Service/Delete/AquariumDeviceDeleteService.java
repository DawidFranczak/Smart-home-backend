package com.smart_home.Device.Factory.Service.Delete;

import com.smart_home.Aquarium.Singleton.AquariumSingleton;
import com.smart_home.Device.Factory.Interface.Delete.IDeviceDelete;
import com.smart_home.Device.Model.Device;
import org.springframework.stereotype.Service;

@Service
public class AquariumDeviceDeleteService implements IDeviceDelete {

    private final AquariumSingleton aquariumSingleton;

    public AquariumDeviceDeleteService(AquariumSingleton aquariumSingleton) {
        this.aquariumSingleton = aquariumSingleton;
    }

    @Override
    public void delete(Device device) {
        aquariumSingleton.delete(device);
    }
}
