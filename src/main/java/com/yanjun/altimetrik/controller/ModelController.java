package com.yanjun.altimetrik.controller;

import com.google.gson.Gson;
import com.yanjun.altimetrik.domain.ReturnModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ModelController {
    @GetMapping("/getModels/{make}")
    public ReturnModel getModels(@PathVariable String make, HttpServletResponse response) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("make", make);
        String url = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/{make}?format=json";
        String returnJson = restTemplate.getForObject(url, String.class, params);
        Gson gson = new Gson();
        ReturnModel returnModel = gson.fromJson(returnJson, ReturnModel.class);
        response.setStatus(HttpStatus.CREATED.value());
        if (returnModel.getResults().size() == 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return returnModel;
    }
}
