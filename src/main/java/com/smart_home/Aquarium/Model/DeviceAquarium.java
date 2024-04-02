package com.smart_home.Aquarium.Model;

import com.smart_home.Device.Model.Device;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAquarium extends Device {

    private String color ="#ffffff";
    private String ledStart="00:00";
    private String ledStop="00:00";
    private String fluoStart="00:00";
    private String fluoStop="00:00";
    private boolean mode=false;
    private boolean ledMode=false;
    private boolean fluoMode=false;

}
