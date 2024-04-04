package com.smart_home.Device.Request;

import com.smart_home.Validation.Anotation.UniqueDeviceName;
import lombok.Data;

@Data
@UniqueDeviceName
public class AddDeviceRequest {
    String name;
    String type;
}
