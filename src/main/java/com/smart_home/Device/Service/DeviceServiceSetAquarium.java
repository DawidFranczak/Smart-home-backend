package com.smart_home.Device.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.Model.DeviceAquarium;
import com.smart_home.Device.Repository.DeviceAquariumRepository;
import com.smart_home.Device.Request.AquariumFluoLightningTimeRequest;
import com.smart_home.Device.Request.AquariumLedLightningTimeRequest;
import com.smart_home.Exception.NotFound404Exception;
import com.smart_home.UDP.UDP;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class DeviceServiceSetAquarium {
    
    private final JwtService jwtService;
    private final DeviceAquariumRepository deviceAquariumRepository;
    private final UDP udp;

    public DeviceServiceSetAquarium(
            JwtService jwtService,
            DeviceAquariumRepository deviceAquariumRepository,
            UDP udp) {
        this.jwtService = jwtService;
        this.deviceAquariumRepository = deviceAquariumRepository;
        this.udp = udp;
    }

    public void setColor(HttpServletRequest request, Long id, String color) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = getAquarium(id, user);
        sendMessageToMicrocontroller(aquarium,color.substring(1));
        saveNewColor(aquarium,color);
        System.out.println(color);
    }

    public void setLedLightningTime(
            HttpServletRequest request,
            Long id,
            AquariumLedLightningTimeRequest form
    ) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = getAquarium(id, user);
        saveNewLedTime(aquarium,form);
        if(aquarium.isMode()) {
            boolean lightningTime = checkLightningTime(aquarium.getLedStart(), aquarium.getLedStop());
            String command = lightningTime ? "ledOn" : "ledOff";
            sendMessageToMicrocontroller(aquarium,command);
        }
    }

    public void setFlouLightningTime(
            HttpServletRequest request,
            Long id,
            AquariumFluoLightningTimeRequest form
    ) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = getAquarium(id, user);
        saveNewFluoTime(aquarium,form);
        if(aquarium.isMode()) {
            boolean lightningTime = checkLightningTime(aquarium.getFluoStart(), aquarium.getFluoStop());
            String command = lightningTime ? "fluoOn" : "fluoOff";
            sendMessageToMicrocontroller(aquarium,command);
        }
    }

    private boolean checkLightningTime(String timeStart, String timeStop){
        LocalTime time = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(timeStart, dateTimeFormatter);
        LocalTime stopTime = LocalTime.parse(timeStop, dateTimeFormatter);

        return (startTime.isBefore(stopTime) && startTime.isBefore(time) && stopTime.isAfter(time)) ||
                (startTime.isAfter(stopTime) && (startTime.isBefore(time) || stopTime.isAfter(time)));
    }

    private void saveNewFluoTime(DeviceAquarium aquarium, AquariumFluoLightningTimeRequest form) {
        if(!form.getFluoStart().isEmpty())
            aquarium.setFluoStart(form.getFluoStart());
        if(!form.getFluoStop().isEmpty())
            aquarium.setFluoStop(form.getFluoStop());
        deviceAquariumRepository.save(aquarium);
    }

    private void saveNewLedTime(DeviceAquarium aquarium, AquariumLedLightningTimeRequest form) {
        if(!form.getLedStart().isEmpty())
            aquarium.setLedStart(form.getLedStart());
        if(!form.getLedStop().isEmpty())
            aquarium.setLedStop(form.getLedStop());
        deviceAquariumRepository.save(aquarium);
    }

    private DeviceAquarium getAquarium(Long id, User user) {
        Optional<DeviceAquarium> optional = deviceAquariumRepository.findAquariumByUserAndId(user, id);
        if (optional.isEmpty())
            throw new NotFound404Exception("Aquarium doesn't exists.");
        return optional.get();
    }

    private void sendMessageToMicrocontroller(DeviceAquarium aquarium, String message) throws IOException {
        udp.send(message, aquarium.getPort(), aquarium.getIp(),40);
    }

    private void saveNewColor(DeviceAquarium aquarium, String color) {
        aquarium.setColor(color);
        deviceAquariumRepository.save(aquarium);
    }
}
