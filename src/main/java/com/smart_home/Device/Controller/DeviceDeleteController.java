package com.smart_home.Device.Controller;

import com.smart_home.Device.Service.DeleteDeviceService;
import com.smart_home.Exception.NotFound404Exception;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/device/delete/")
@CrossOrigin
public class DeviceDeleteController {

    private final DeleteDeviceService deleteDeviceService;

    public DeviceDeleteController(DeleteDeviceService deleteDeviceService) {
        this.deleteDeviceService = deleteDeviceService;
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<Void> deleteDevice(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        try {
            deleteDeviceService.deleteDevice(id, request);
        } catch (ClassNotFoundException e) {
            throw new NotFound404Exception("Unidentified device type.");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
