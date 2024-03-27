package com.smart_home.Device.Repository;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Device.Model.DeviceAquarium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DeviceAquariumRepository extends JpaRepository<DeviceAquarium,Long> {

    @Query("""
            SELECT da
            FROM DeviceAquarium da
            JOIN Device d
            ON d.id = da.id
            where d.user = ?1 AND da.id = ?2
            """)
    Optional<DeviceAquarium> findAquariumByUserAndId(User user, Long id);
}
