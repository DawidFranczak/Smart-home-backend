package com.smart_home.Device.Factory.Service;

import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Factory.Interface.IDeviceFactory;
import com.smart_home.Device.Factory.Interface.IDeviceSave;
import org.springframework.stereotype.Component;


@Component
public class DeviceFactoryService implements IDeviceFactory {

    private final AquariumDeviceSaveService aquariumDeviceSaveService;
    private final StairsDeviceSaveService stairsDeviceSaveService;

    public DeviceFactoryService(
            AquariumDeviceSaveService aquariumDeviceSaveService,
            StairsDeviceSaveService stairsDeviceSaveService
    ) {
        this.aquariumDeviceSaveService = aquariumDeviceSaveService;
        this.stairsDeviceSaveService = stairsDeviceSaveService;
    }

    @Override
    public IDeviceSave create(DeviceType deviceType) throws ClassNotFoundException {
        switch (deviceType) {
            case AQUARIUM -> {
                return aquariumDeviceSaveService;
            }
            case STAIRS -> {
                return stairsDeviceSaveService;
            }
            default -> {
                throw new ClassNotFoundException("Device type "+deviceType+" not found.");
            }
        }
    }
}
