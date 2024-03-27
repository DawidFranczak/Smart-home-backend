package com.smart_home.Device.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AquariumColorRequest {
    @NotBlank(message = "Color can't be null.")
    private String color;
}
