package com.smart_home.Device.Factory.Interface;

import com.smart_home.Device.Enum.DeviceType;

public interface IDeviceFactory {
    public IDeviceSave create(DeviceType deviceType) throws ClassNotFoundException;
}
