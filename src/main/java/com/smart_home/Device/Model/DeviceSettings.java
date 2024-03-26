package com.smart_home.Device.Model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DeviceSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
    private String value;
}
