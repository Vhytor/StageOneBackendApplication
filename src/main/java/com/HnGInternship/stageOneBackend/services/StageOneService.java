package com.HnGInternship.stageOneBackend.services;

import java.io.IOException;

public interface StageOneService {
    String getGreeting(String visitorName,String clientIp) throws IOException;
}
