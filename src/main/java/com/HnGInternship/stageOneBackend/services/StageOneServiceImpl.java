package com.HnGInternship.stageOneBackend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class StageOneServiceImpl implements StageOneService{

//    @Override
//    public String getGreeting(String visitorName, String clientIp) throws IOException {
//        String location = getLocation(clientIp);
//        String temperature = getTemperature(location);
//        return String.format("{\"client_ip\": \"%s\",\"location\": \"%s\", \"greeting\": \"Hello, %s!, the temperature is %s degree Celsius in %s\"}",
//                clientIp,location,visitorName,temperature,location);
//    }
//
//    private String getLocation(String ip) {
////        Url url = new URL("https://ipinfo.io" + ip + "/json");
//        return "new york";
//    }
//    private String getTemperature(String location) throws IOException{
//        return "11";
//    }











    private static final Logger logger = Logger.getLogger(StageOneServiceImpl.class.getName());

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Override
    public String getLocation(String clientIp) {
        RestTemplate restTemplate = new RestTemplate();
        if ("127.0.0.1".equals(clientIp)) {
            clientIp = "8.8.8.8";
        }
        String apiUrl = String.format("http://ip-api.com/json/%s", clientIp);

        try {
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            logger.info("IP API Response: " + response);
            if (response != null && "success".equals(response.get("status"))){
                return (String) response.get("city");
            }else {
                logger.warning("Error retrieving location: " + response);
                return "Unknown";
            }
        }catch (Exception e){
            logger.severe("Error fetching location: " + e.getMessage());
            return "Unknown";
        }
    }
    @Override
    public String getGreeting(String visitorName, int temperature, String location) {
        return String.format("Hello, %s!, the temperature is %d degrees Celsius in %s", visitorName, temperature, location);
    }

    @Override
    public int getTemperature(String location) {
        if ("Unknown".equals(location)) {
            return 0;
        }
        RestTemplate restTemplate = new RestTemplate();
//        String apiKey = "YOUR_API_KEY";
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s", location, weatherApiKey);

        try {
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            logger.info("Weather API Response: " + response);
            if (response != null && response.containsKey("main")){
                Map<String, Object> main = response != null ? (Map<String, Object>) response.get("main") : null;
                return (int) ((double) main.get("temp"));
            }else {
                logger.warning("Error retrieving temperature: " + response);
                return 0;
            }
        }catch (HttpClientErrorException e){
            logger.severe("Error fetching temperature: " + e.getMessage());
            return 0;
        }
    }


    @Override
    public String getExternalIp(){
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.ipify.org?format=json";
        try {
            Map<String, String> response = restTemplate.getForObject(apiUrl, Map.class);
            logger.info("External IP API Response: " + response);
            return response != null ? response.get("ip") : "127.0.0.1";
        } catch (Exception e) {
            logger.severe("Error fetching external IP: " + e.getMessage());
            return "127.0.0.1";
        }

    }
}
