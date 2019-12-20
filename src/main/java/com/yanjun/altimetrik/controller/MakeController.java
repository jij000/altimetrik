package com.yanjun.altimetrik.controller;

import com.google.gson.Gson;
import com.yanjun.altimetrik.domain.ReturnMake;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MakeController {
    @GetMapping("/getMakes/{type}")
    public ReturnMake getMakes(@PathVariable String type, HttpServletResponse response) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        String url = "https://vpic.nhtsa.dot.gov/api/vehicles/GetMakesForVehicleType/{type}?format=json";
        String returnJson = restTemplate.getForObject(url, String.class, params);
        Gson gson = new Gson();
        ReturnMake returnMake = gson.fromJson(returnJson, ReturnMake.class);
        response.setStatus(HttpStatus.CREATED.value());
        if (returnMake.getResults().size() == 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return returnMake;
    }
}
