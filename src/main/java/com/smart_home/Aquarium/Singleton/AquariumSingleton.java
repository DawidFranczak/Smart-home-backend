package com.smart_home.Aquarium.Singleton;

import com.smart_home.Aquarium.Model.DeviceAquarium;
import com.smart_home.Aquarium.Repository.DeviceAquariumRepository;
import com.smart_home.Aquarium.Request.Repository.DeviceRepository;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Exception.NotFound404Exception;
import com.smart_home.UDP.UDP;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AquariumSingleton {

    private final DeviceAquariumRepository deviceAquariumRepository;
    private final UDP udp;
    private final DeviceRepository deviceRepository;

    private AquariumSingleton(
            DeviceAquariumRepository deviceAquariumRepository,
            UDP udp,
            DeviceRepository deviceRepository) {
        this.deviceAquariumRepository = deviceAquariumRepository;
        this.udp = udp;
        this.deviceRepository = deviceRepository;
    }

    public void sendCommand(DeviceAquarium aquarium, String command) throws IOException {
        udp.send(command, aquarium.getPort(), aquarium.getIp(),40);
    }

    public boolean checkLightningTime(String timeStart, String timeStop){
        LocalTime time = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(timeStart, dateTimeFormatter);
        LocalTime stopTime = LocalTime.parse(timeStop, dateTimeFormatter);

        return (startTime.isBefore(stopTime) && startTime.isBefore(time) && stopTime.isAfter(time)) ||
                (startTime.isAfter(stopTime) && (startTime.isBefore(time) || stopTime.isAfter(time)));
    }

    public DeviceAquarium getAquarium(Long id, User user) {
        Optional<DeviceAquarium> optional = deviceAquariumRepository.findAquariumByUserAndId(user, id);
        if (optional.isEmpty())
            throw new NotFound404Exception("Aquarium doesn't exists.");
        return optional.get();
    }
}
