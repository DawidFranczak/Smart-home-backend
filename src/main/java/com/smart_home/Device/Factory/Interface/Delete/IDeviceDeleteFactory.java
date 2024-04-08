package com.smart_home.Device.Factory.Interface.Delete;

import com.smart_home.Device.Model.Device;

public interface IDeviceDeleteFactory {

    public IDeviceDelete delete(Device device) throws ClassNotFoundException;
}
