package com.smart_home.Aquarium.Controller;

import com.smart_home.Aquarium.DTO.DeviceAquariumDTO;
import com.smart_home.Aquarium.Request.AquariumColorRequest;
import com.smart_home.Aquarium.Request.AquariumFluoLightningTimeRequest;
import com.smart_home.Aquarium.Request.AquariumLedLightningTimeRequest;
import com.smart_home.Aquarium.Request.AquariumModeRequest;
import com.smart_home.Aquarium.Service.DeviceServiceGetAquarium;
import com.smart_home.Aquarium.Service.DeviceServiceSetAquarium;
import com.smart_home.Device.DTO.DeviceDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/device/aquarium/")
@CrossOrigin
public class AquariumController {

    private final DeviceServiceGetAquarium deviceServiceAquarium;
    private final DeviceServiceSetAquarium deviceServiceSetAquarium;

    public AquariumController(
            DeviceServiceGetAquarium deviceServiceAquarium,
            DeviceServiceSetAquarium deviceServiceSetAquarium
    ) {
        this.deviceServiceAquarium = deviceServiceAquarium;
        this.deviceServiceSetAquarium = deviceServiceSetAquarium;
    }

    @GetMapping("get/all/")
    public ResponseEntity<List<DeviceDTO>> getAllUserAquariums(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(deviceServiceAquarium.getAll(request));
    }

    @GetMapping("get/{id}/")
    public ResponseEntity<DeviceAquariumDTO> getAquariumSettings(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(deviceServiceAquarium.getSettings(request,id));
    }

    @PutMapping("set/color/{id}/")
    public ResponseEntity<Void> setAquariumSettings(
            HttpServletRequest request,
            @PathVariable Long id,
            @Valid @RequestBody AquariumColorRequest color
    ){
        try{
            deviceServiceSetAquarium.setColor(request,id,color.getColor());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
    }

    @PutMapping("set/led/{id}/")
    public ResponseEntity<Void> setAquariumLedTime(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody AquariumLedLightningTimeRequest form
    ){
        try {
            deviceServiceSetAquarium.setLedLightningTime(request, id, form);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
    }

    @PutMapping("set/fluo/{id}/")
    public ResponseEntity<Void> setAquariumFluoTime(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody AquariumFluoLightningTimeRequest form
    ){
        try {
            deviceServiceSetAquarium.setFlouLightningTime(request, id, form);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
    }
    @PutMapping("set/mode/{id}/")
    public ResponseEntity<Void> setAquariumMode(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody AquariumModeRequest form
    ){
        try{
            deviceServiceSetAquarium.setMode(request,id,form);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
    }
    @PutMapping("set/ledMode/{id}/")
    public ResponseEntity<Void> setAquariumLedMode(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody AquariumModeRequest form
    ){
        try{
            deviceServiceSetAquarium.setLedMode(request,id,form);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
    }

    @PutMapping("set/fluoMode/{id}/")
    public ResponseEntity<Void> setAquariumFluoMode(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody AquariumModeRequest form
    ){
        try{
            deviceServiceSetAquarium.setFluoMode(request,id,form);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
    }



}
