package com.smart_home.Device.Factory.Service.Save;

import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Factory.Interface.Save.IDeviceSaveFactory;
import com.smart_home.Device.Factory.Interface.Save.IDeviceSave;
import org.springframework.stereotype.Component;


@Component
public class DeviceSaveFactoryService implements IDeviceSaveFactory {

    private final AquariumDeviceSaveService aquariumDeviceSaveService;
    private final StairsDeviceSaveService stairsDeviceSaveService;

    public DeviceSaveFactoryService(
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
