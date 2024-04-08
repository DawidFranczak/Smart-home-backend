package com.smart_home.Device.Factory.Interface.Save;

import com.smart_home.Device.Enum.DeviceType;

public interface IDeviceSaveFactory {
    public IDeviceSave create(DeviceType deviceType) throws ClassNotFoundException;
}
