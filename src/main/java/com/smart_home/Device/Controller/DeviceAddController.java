package com.smart_home.Device.Controller;


import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Device.Service.AddNewDeviceService;
import com.smart_home.Device.Service.AddRandomDeviceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/device/")
@CrossOrigin
public class DeviceAddController {

    private final AddNewDeviceService addNewDeviceService;
    private final AddRandomDeviceService addRandomDeviceService;

    public DeviceAddController(
            AddNewDeviceService addNewDeviceService,
            AddRandomDeviceService addRandomDeviceService
    ) {
        this.addNewDeviceService = addNewDeviceService;
        this.addRandomDeviceService = addRandomDeviceService;
    }

    @PostMapping("add/")
    public ResponseEntity<Void> addNewDevice(
            HttpServletRequest request,
            @Valid @RequestBody AddDeviceRequest form
        ){
        try {
            addNewDeviceService.addNewDevice(request,form);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("add/random/")
    public ResponseEntity<Void> addRandomDevice(HttpServletRequest request){
        addRandomDeviceService.add(request);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
