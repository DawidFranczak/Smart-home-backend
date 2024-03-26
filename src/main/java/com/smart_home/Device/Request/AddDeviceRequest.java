package com.smart_home.Device.Request;

import com.smart_home.Validation.Anotation.UniqueDeviceName;
import lombok.Data;

@Data
public class AddDeviceRequest {
    @UniqueDeviceName
    String name;
    String type;

}
