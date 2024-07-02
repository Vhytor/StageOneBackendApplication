package com.HnGInternship.stageOneBackend.services;

import java.io.IOException;

public interface StageOneService {
//    String getGreeting(String visitorName,String clientIp) throws IOException;


    String getLocation(String clientIp);
    int getTemperature(String location);
    String getGreeting(String visitorName, int temperature, String location);

}
