package com.smart_home.Device.DTO;

public record DeviceAquariumDTO(
        Long id,
        String name,
        String color,
        String ledStart,
        String ledStop,
        String fluoStart,
        String fluoStop,
        boolean mode,
        boolean ledMode,
        boolean fluoMode
) {
}
