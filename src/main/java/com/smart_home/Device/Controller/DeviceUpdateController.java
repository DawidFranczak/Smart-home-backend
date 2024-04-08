package com.smart_home.Device.Controller;

import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Device.Service.UpdateDeviceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/device/update/")
@CrossOrigin
public class DeviceUpdateController {

    private final UpdateDeviceService updateDeviceService;

    public DeviceUpdateController(UpdateDeviceService updateDeviceService) {
        this.updateDeviceService = updateDeviceService;
    }

    @PutMapping("{id}/")
    public ResponseEntity<Void> updateDevice(
            HttpServletRequest request,
            @Valid @RequestBody AddDeviceRequest form,
            @PathVariable Long id
    ){
        updateDeviceService.updateDevice(id, request, form);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
