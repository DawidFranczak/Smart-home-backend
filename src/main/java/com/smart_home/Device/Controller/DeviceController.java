package com.smart_home.Device.Controller;


import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Device.Service.DeviceServiceAddNewDevice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@RestController
@RequestMapping("api/device/")
@CrossOrigin
public class DeviceController {

    private final DeviceServiceAddNewDevice deviceServiceAddNewDevice;

    public DeviceController(DeviceServiceAddNewDevice deviceServiceAddNewDevice) {
        this.deviceServiceAddNewDevice = deviceServiceAddNewDevice;
    }

    @PostMapping("add/")
    public ResponseEntity<Void> addNewDevice(
            HttpServletRequest request,
            @RequestBody AddDeviceRequest form
        ) throws UnknownHostException {

        deviceServiceAddNewDevice.addNewDevice(request,form);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
