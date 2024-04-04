package com.smart_home.Device.Factory.Interface;

import com.smart_home.Authentication.Model.User;

import java.net.DatagramPacket;

public interface IDeviceSave {

    public void save(User user, DatagramPacket deviceInfo, String name);
}
