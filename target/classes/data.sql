--////////////////////////////////////////PORT//////////////////////////////
INSERT INTO device_settings (settings_key,setting_value)
SELECT 'aquariumPort','7863'
WHERE NOT EXISTS (
    SELECT 1 FROM device_settings WHERE settings_key = 'aquariumPort'
);

INSERT INTO device_settings (settings_key,setting_value)
SELECT 'stairsPort','1234'
WHERE NOT EXISTS (
    SELECT 1 FROM device_settings WHERE settings_key = 'stairsPort'
);


--////////////////////////////////////////PASSWORD////////////////////////////
INSERT INTO device_settings (settings_key,setting_value)
SELECT 'aquariumAddingPassword','aquariumPassword'
WHERE NOT EXISTS (
    SELECT 1 FROM device_settings WHERE settings_key = 'aquariumAddingPassword'
);

INSERT INTO device_settings (settings_key,setting_value)
SELECT 'stairsAddingPassword','stairsPassword'
WHERE NOT EXISTS (
    SELECT 1 FROM device_settings WHERE settings_key = 'stairsAddingPassword'
);
