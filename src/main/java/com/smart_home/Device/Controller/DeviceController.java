package com.smart_home.Device.Controller;


import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Device.Service.DeviceServiceAddNewDevice;
import com.smart_home.Device.Service.DeviceServiceAddRandomDevice;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@RestController
@RequestMapping("api/device/")
@CrossOrigin
public class DeviceController {

    private final DeviceServiceAddNewDevice deviceServiceAddNewDevice;
    private final DeviceServiceAddRandomDevice deviceServiceAddRandomDevice;

    public DeviceController(
            DeviceServiceAddNewDevice deviceServiceAddNewDevice,
            DeviceServiceAddRandomDevice deviceServiceAddRandomDevice
    ) {
        this.deviceServiceAddNewDevice = deviceServiceAddNewDevice;
        this.deviceServiceAddRandomDevice = deviceServiceAddRandomDevice;
    }

    @PostMapping("add/")
    public ResponseEntity<Void> addNewDevice(
            HttpServletRequest request,
            @Valid @RequestBody AddDeviceRequest form
        ){
        try {
            deviceServiceAddNewDevice.addNewDevice(request,form);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("add/random/")
    public ResponseEntity<Void> addRandomDevice(HttpServletRequest request){
        deviceServiceAddRandomDevice.add(request);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
