--////////////////////////////////////////PORT//////////////////////////////
INSERT INTO device_settings (key,value)
SELECT 'aquariumPort','1234'
WHERE NOT EXISTS (
    SELECT 1 FROM device_settings WHERE key = 'aquariumPort'
);

INSERT INTO device_settings (key,value)
SELECT 'stairsPort','1234'
WHERE NOT EXISTS (
    SELECT 1 FROM device_settings WHERE key = 'stairsPort'
);


--////////////////////////////////////////PASSWORD////////////////////////////
INSERT INTO device_settings (key,value)
SELECT 'aquariumAddingPassword','aquariumPassword'
WHERE NOT EXISTS (
    SELECT 1 FROM device_settings WHERE key = 'aquariumAddingPassword'
);

INSERT INTO device_settings (key,value)
SELECT 'stairsAddingPassword','stairsPassword'
WHERE NOT EXISTS (
    SELECT 1 FROM device_settings WHERE key = 'stairsAddingPassword'
);
