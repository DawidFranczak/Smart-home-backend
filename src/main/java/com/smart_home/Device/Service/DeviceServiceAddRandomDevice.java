package com.smart_home.Device.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Aquarium.Model.DeviceAquarium;
import com.smart_home.Device.Model.DeviceStairs;
import com.smart_home.Aquarium.Repository.DeviceAquariumRepository;
import com.smart_home.Device.Repository.DeviceStairsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Random;
@Service
public class DeviceServiceAddRandomDevice {

    private final JwtService jwtService;
    private final DeviceAquariumRepository deviceAquariumRepository;
    private final DeviceStairsRepository deviceStairsRepository;

    public DeviceServiceAddRandomDevice(
            JwtService jwtService,
            DeviceAquariumRepository deviceAquariumRepository,
            DeviceStairsRepository deviceStairsRepository
    ) {
        this.jwtService = jwtService;
        this.deviceAquariumRepository = deviceAquariumRepository;
        this.deviceStairsRepository = deviceStairsRepository;
    }

    public void add(HttpServletRequest request) {
        User user = jwtService.extractUser(request);
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int deviceType = random.nextInt(2);
            if (deviceType == 0) {
                createRandomDeviceAquarium(random, user);
            } else {
                createRandomDeviceStairs(random, user);
            }
        }
    }

    private void createRandomDeviceAquarium(Random random, User user) {
        DeviceAquarium device = new DeviceAquarium();
        device.setName("AquariumDevice" + random.nextInt(1000));
        device.setIp(generateRandomIp(random));
        device.setPort(random.nextInt(65536));
        device.setUser(user);
        device.setType(DeviceType.AQUARIUM);
        deviceAquariumRepository.save(device);
    }

    private void createRandomDeviceStairs(Random random, User user) {
        DeviceStairs device = new DeviceStairs();
        device.setName("StairsDevice" + random.nextInt(1000));
        device.setIp(generateRandomIp(random));
        device.setPort(random.nextInt(65536));
        device.setUser(user);
        device.setType(DeviceType.STAIRS);
        deviceStairsRepository.save(device);
    }

    private InetAddress generateRandomIp(Random random) {
        byte[] ip = new byte[4];
        random.nextBytes(ip);
        try {
            return InetAddress.getByAddress(ip);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
