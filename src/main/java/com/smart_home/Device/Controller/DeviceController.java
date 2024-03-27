package com.smart_home.Device.Controller;


import com.smart_home.Device.DTO.DeviceAquariumDTO;
import com.smart_home.Device.DTO.DeviceDTO;
import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Device.Request.AquariumColorRequest;
import com.smart_home.Device.Request.AquariumFluoLightningTimeRequest;
import com.smart_home.Device.Request.AquariumLedLightningTimeRequest;
import com.smart_home.Device.Service.DeviceServiceAddNewDevice;
import com.smart_home.Device.Service.DeviceServiceAddRandomDevice;
import com.smart_home.Device.Service.DeviceServiceGetAquarium;
import com.smart_home.Device.Service.DeviceServiceSetAquarium;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("api/device/")
@CrossOrigin
public class DeviceController {

    private final DeviceServiceAddNewDevice deviceServiceAddNewDevice;
    private final DeviceServiceAddRandomDevice deviceServiceAddRandomDevice;
    private final DeviceServiceGetAquarium deviceServiceAquarium;
    private final DeviceServiceSetAquarium deviceServiceSetAquarium;

    public DeviceController(
            DeviceServiceAddNewDevice deviceServiceAddNewDevice,
            DeviceServiceAddRandomDevice deviceServiceAddRandomDevice,
            DeviceServiceGetAquarium deviceServiceAquarium,
            DeviceServiceSetAquarium deviceServiceSetAquarium) {
        this.deviceServiceAddNewDevice = deviceServiceAddNewDevice;
        this.deviceServiceAddRandomDevice = deviceServiceAddRandomDevice;
        this.deviceServiceAquarium = deviceServiceAquarium;
        this.deviceServiceSetAquarium = deviceServiceSetAquarium;
    }

    @PostMapping("add/")
    public ResponseEntity<Void> addNewDevice(
            HttpServletRequest request,
            @RequestBody AddDeviceRequest form
        ) throws UnknownHostException {

        deviceServiceAddNewDevice.addNewDevice(request,form);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("aquarium/get/all/")
    public ResponseEntity<List<DeviceDTO>> getAllUserAquariums(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(deviceServiceAquarium.getAll(request));
    }

    @GetMapping("aquarium/get/{id}/")
    public ResponseEntity<DeviceAquariumDTO> getAquariumSettings(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(deviceServiceAquarium.getSettings(request,id));
    }

    @PutMapping("aquarium/set/color/{id}/")
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

    @PutMapping("aquarium/set/led/{id}/")
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

    @PutMapping("aquarium/set/fluo/{id}/")
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

    @GetMapping("add/random/")
    public ResponseEntity<Void> addRandomDevice(HttpServletRequest request){
        deviceServiceAddRandomDevice.add(request);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
