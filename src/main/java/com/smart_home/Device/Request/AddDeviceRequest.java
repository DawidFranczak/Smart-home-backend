package com.smart_home.Device.Request;

import com.smart_home.Validation.Anotation.UniqueDeviceName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@UniqueDeviceName
public class AddDeviceRequest {
    @NotNull
    String name;
    @NotNull
    String type;
}
