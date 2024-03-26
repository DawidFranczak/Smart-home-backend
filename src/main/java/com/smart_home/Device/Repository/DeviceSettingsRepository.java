package com.smart_home.Device.Repository;

import com.smart_home.Device.Model.DeviceSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceSettingsRepository extends JpaRepository<DeviceSettings, Long> {
    Optional<DeviceSettings> findByKey(String key);
}
