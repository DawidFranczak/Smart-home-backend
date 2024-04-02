package com.smart_home.Aquarium.Service;

import com.smart_home.Aquarium.Singleton.AquariumSingleton;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Aquarium.Model.DeviceAquarium;
import com.smart_home.Aquarium.Repository.DeviceAquariumRepository;
import com.smart_home.Aquarium.Request.AquariumFluoLightningTimeRequest;
import com.smart_home.Aquarium.Request.AquariumLedLightningTimeRequest;
import com.smart_home.Aquarium.Request.AquariumModeRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class DeviceServiceSetAquarium {
    
    private final JwtService jwtService;
    private final DeviceAquariumRepository deviceAquariumRepository;
    private final AquariumSingleton aquariumSingleton;

    public DeviceServiceSetAquarium(
            JwtService jwtService,
            DeviceAquariumRepository deviceAquariumRepository,
            AquariumSingleton aquariumSingleton) {
        this.jwtService = jwtService;
        this.deviceAquariumRepository = deviceAquariumRepository;
        this.aquariumSingleton = aquariumSingleton;
    }

    public void setColor(HttpServletRequest request, Long id, String color) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = aquariumSingleton.getAquarium(id, user);
        aquariumSingleton.sendCommand(aquarium,color.substring(1));
        saveNewColor(aquarium,color);
        System.out.println(color);
    }

    public void setLedLightningTime(
            HttpServletRequest request,
            Long id,
            AquariumLedLightningTimeRequest form
    ) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = aquariumSingleton.getAquarium(id, user);
        saveNewLedTime(aquarium,form);
        if(aquarium.isMode()) {
            boolean lightningTime = aquariumSingleton.checkLightningTime(aquarium.getLedStart(), aquarium.getLedStop());
            String command = lightningTime ? "ledOn" : "ledOff";
            aquariumSingleton.sendCommand(aquarium,command);
        }
    }

    public void setFlouLightningTime(
            HttpServletRequest request,
            Long id,
            AquariumFluoLightningTimeRequest form
    ) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = aquariumSingleton.getAquarium(id, user);
        saveNewFluoTime(aquarium,form);
        if(aquarium.isMode()) {
            boolean lightningTime = aquariumSingleton.checkLightningTime(aquarium.getFluoStart(), aquarium.getFluoStop());
            String command = lightningTime ? "fluoOn" : "fluoOff";
            aquariumSingleton.sendCommand(aquarium,command);
        }
    }
    public void setMode(HttpServletRequest request, Long id, AquariumModeRequest form) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = aquariumSingleton.getAquarium(id, user);

        boolean isFluoTime = aquariumSingleton.checkLightningTime(aquarium.getFluoStart(), aquarium.getFluoStop());
        aquariumSingleton.sendCommand(aquarium, isFluoTime ? "fluoOn" : "fluoOff");

        boolean isLedTime = aquariumSingleton.checkLightningTime(aquarium.getLedStart(), aquarium.getLedStop());
        aquariumSingleton.sendCommand(aquarium, isLedTime ? "ledOn" : "ledOff");

        aquarium.setMode(form.isMode());
        deviceAquariumRepository.save(aquarium);
    }

    public void setLedMode(HttpServletRequest request, Long id, AquariumModeRequest form) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = aquariumSingleton.getAquarium(id, user);
        String command = form.isMode() ? "ledOn" : "ledOff";
        aquariumSingleton.sendCommand(aquarium,command);
        aquarium.setLedMode(form.isMode());
        deviceAquariumRepository.save(aquarium);
    }

    public void setFluoMode(HttpServletRequest request, Long id, AquariumModeRequest form) throws IOException {
        User user = jwtService.extractUser(request);
        DeviceAquarium aquarium = aquariumSingleton.getAquarium(id, user);
        String command = form.isMode() ? "fluoOn" : "fluoOff";
        aquariumSingleton.sendCommand(aquarium,command);
        aquarium.setFluoMode(form.isMode());
        deviceAquariumRepository.save(aquarium);
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

    private void saveNewColor(DeviceAquarium aquarium, String color) {
        aquarium.setColor(color);
        deviceAquariumRepository.save(aquarium);
    }
}
