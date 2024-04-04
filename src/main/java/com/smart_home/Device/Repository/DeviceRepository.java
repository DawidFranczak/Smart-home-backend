package com.smart_home.Device.Repository;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("""
            SELECT EXISTS(SELECT 1 FROM Device d WHERE d.name = ?1 AND d.type = ?2 AND d.user = ?3)
            """)
    boolean deviceExists(String name, DeviceType type, User user);

    List<Device> findByUserAndType(User user, DeviceType type);
    List<Device> findByUser(User user);
}
