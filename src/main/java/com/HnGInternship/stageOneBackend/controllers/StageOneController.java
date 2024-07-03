package com.HnGInternship.stageOneBackend.controllers;

import com.HnGInternship.stageOneBackend.services.StageOneService;
import com.HnGInternship.stageOneBackend.services.StageOneServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class StageOneController {

    @Autowired
    private StageOneService stageOneService;

//    @GetMapping("/api/stage_one")
//    public String getGreeting(@RequestParam String visitor_name, HttpServletRequest httpServletRequest) throws IOException {
//        String clientIp = httpServletRequest.getHeader(" X-Forwarded-For");
//        if (clientIp == null) {
//            clientIp = httpServletRequest.getRemoteAddr();
//        }
//        System.out.println("clientIp" + clientIp);
////        String clientIp = httpServletRequest.getRemoteAddr();
////        if (clientIp.equals("0:0:0:0:0:0:0:1")){
////            clientIp = "127.0.0.1";
////        }
//        return stageOneService.getGreeting(visitor_name,clientIp);
//    }

    @GetMapping("/api/stage_one")
    public Map<String, Object> stage(@RequestParam String visitor_name, @RequestHeader(value = "X-Forwarded-For", required = false) String clientIp) {
//        String clientIp = request.getRemoteAddr();
//        if (clientIp.equals("0:0:0:0:0:0:0:1")) {
//            clientIp = "127.0.0.1";
//        }
        if (clientIp == null || clientIp.isEmpty() || clientIp.equals("127.0.0.1") || clientIp.equals("0:0:0:0:0:0:0:1")) {
            clientIp = stageOneService.getExternalIp();
        }

        String location = stageOneService.getLocation(clientIp);
        int temperature = stageOneService.getTemperature(location);
        String greeting = stageOneService.getGreeting(visitor_name, temperature, location);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("client_ip", clientIp);
        response.put("location", location);
        response.put("greeting", greeting);

        return response;
    }
    private String getClientIp(HttpServletRequest httpServletRequest){
        String clientIp = httpServletRequest.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)){
            clientIp = httpServletRequest.getRemoteAddr();
        }
        return clientIp;
    }

}
