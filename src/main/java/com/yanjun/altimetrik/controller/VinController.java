package com.yanjun.altimetrik.controller;

import com.google.gson.Gson;
import com.yanjun.altimetrik.domain.ReturnVin;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VinController {
    @GetMapping("/getVinInfo/{vin}")
    public ReturnVin getVinInfo(@PathVariable String vin, HttpServletResponse response) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("vin", vin);
        String url = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/{vin}?format=json";
        String returnJson = restTemplate.getForObject(url, String.class, params);
        Gson gson = new Gson();
        ReturnVin returnVin = gson.fromJson(returnJson, ReturnVin.class);
        response.setStatus(HttpStatus.CREATED.value());
        if (returnVin.getResults().size() == 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return returnVin;
    }
}
