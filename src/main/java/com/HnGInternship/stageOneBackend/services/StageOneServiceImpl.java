package com.HnGInternship.stageOneBackend.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StageOneServiceImpl implements StageOneService{
    @Override
    public String getGreeting(String visitorName, String clientIp) throws IOException {
        String location = getLocation(clientIp);
        String temperature = getTemperature(location);
        return String.format("{\"client_ip\": \"%s\",\"location\": \"%s\", \"greeting\": \"Hello, %s!, the temperature is %s degree Celsius in %s\"}",
                clientIp,location,visitorName,temperature,location);
    }

    private String getLocation(String ip) {
        return "new york";
    }
    private String getTemperature(String location) throws IOException{
        return "11";
    }
}
