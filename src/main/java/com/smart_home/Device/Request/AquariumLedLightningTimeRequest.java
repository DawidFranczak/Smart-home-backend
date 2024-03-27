package com.smart_home.Device.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AquariumLedLightningTimeRequest {

    private String ledStart;
    private String ledStop;
}
