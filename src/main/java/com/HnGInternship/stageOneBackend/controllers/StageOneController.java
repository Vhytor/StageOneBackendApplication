package com.HnGInternship.stageOneBackend.controllers;

import com.HnGInternship.stageOneBackend.services.StageOneService;
import com.HnGInternship.stageOneBackend.services.StageOneServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class StageOneController {

    @Autowired
    private StageOneService stageOneService;

    @GetMapping("/api/stage_one")
    public String getGreeting(@RequestParam String visitor_name, HttpServletRequest httpServletRequest) throws IOException {
        String clientIp = httpServletRequest.getRemoteAddr();
        if (clientIp.equals("0:0:0:0:0:0:0:1")){
            clientIp = "127.0.0.1";
        }
        return stageOneService.getGreeting(visitor_name,clientIp);
    }
}
