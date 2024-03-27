package com.smart_home.Device.Model;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Device.Enum.DeviceType;
import jakarta.persistence.*;
import lombok.*;

import java.net.InetAddress;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    @Enumerated(EnumType.STRING)
    protected DeviceType type;

    protected InetAddress ip;
    protected int port;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    protected User user;
}
