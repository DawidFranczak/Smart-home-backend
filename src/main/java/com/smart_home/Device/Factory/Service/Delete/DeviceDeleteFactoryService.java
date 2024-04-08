package com.smart_home.Device.Factory.Service.Delete;

import com.smart_home.Device.Factory.Interface.Delete.IDeviceDelete;
import com.smart_home.Device.Factory.Interface.Delete.IDeviceDeleteFactory;
import com.smart_home.Device.Model.Device;
import org.springframework.stereotype.Service;

@Service
public class DeviceDeleteFactoryService implements IDeviceDeleteFactory {
    private final AquariumDeviceDeleteService aquariumDeviceDeleteService;

    public DeviceDeleteFactoryService(AquariumDeviceDeleteService aquariumDeviceDeleteService) {
        this.aquariumDeviceDeleteService = aquariumDeviceDeleteService;
    }

    @Override
    public IDeviceDelete delete(Device device) throws ClassNotFoundException {
        switch (device.getType()){
            case AQUARIUM -> {
                return aquariumDeviceDeleteService;
            }
            case STAIRS -> {
                return null;
            }
            default -> {
                throw new ClassNotFoundException("Device type "+device.getType()+" not found.");
            }
        }
    }
}
