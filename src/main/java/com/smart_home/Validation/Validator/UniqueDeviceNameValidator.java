package com.smart_home.Validation.Validator;

import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.Enum.DeviceType;
import com.smart_home.Device.Repository.DeviceRepository;
import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Validation.Anotation.UniqueDeviceName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UniqueDeviceNameValidator implements ConstraintValidator<UniqueDeviceName, AddDeviceRequest> {
    private final DeviceRepository deviceRepository;
    private final JwtService jwtService;

    public UniqueDeviceNameValidator(DeviceRepository deviceRepository, JwtService jwtService) {
        this.deviceRepository = deviceRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void initialize(UniqueDeviceName constraintAnnotation) {
    }

    @Override
    public boolean isValid(AddDeviceRequest addDeviceRequest, ConstraintValidatorContext constraintValidatorContext) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String name = addDeviceRequest.getName();
        String type = addDeviceRequest.getType();
        DeviceType deviceType = DeviceType.valueOf(type.toUpperCase());
        return !deviceRepository.deviceExists(name,deviceType,jwtService.extractUser(request));
    }
}
