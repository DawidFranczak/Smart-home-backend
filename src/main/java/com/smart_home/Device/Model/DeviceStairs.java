package com.smart_home.Device.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStairs extends Device{
    private int steps = 200;
    private int brightness = 100;
    private int lightTime = 6;
    private boolean mode = false;

}
