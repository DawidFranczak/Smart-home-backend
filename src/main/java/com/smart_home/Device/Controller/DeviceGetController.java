package com.smart_home.Device.Controller;

import com.smart_home.Device.DTO.DeviceDTO;
import com.smart_home.Device.Service.GetDeviceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/device/get/")
@CrossOrigin
public class DeviceGetController {

    private final GetDeviceService getDeviceService;

    public DeviceGetController(GetDeviceService getDeviceService) {
        this.getDeviceService = getDeviceService;
    }

    @GetMapping("all/")
    public ResponseEntity<List<DeviceDTO>> getAllDevice (
            HttpServletRequest request
    ){
        return new ResponseEntity<>(getDeviceService.getAll(request), HttpStatus.OK);
    }

}
