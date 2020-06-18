package com.example.watermelon.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.example.watermelon.dto.ChartResponseDto;
import com.example.watermelon.service.ChartService;
import org.json.JSONObject;

@RestController
public class ChartController {
    @Autowired
    ChartService chartService;

    @GetMapping(path = "/chart")
    public List<ChartResponseDto> Chart() {
        return chartService.getChartList();
    }

    @GetMapping(path = "/oauth2/token")
    public ResponseEntity<?> tokenCallback(@RequestParam Map<String, String> map) {
        return ResponseEntity.ok()
                             .body(map);
    }

    @GetMapping(path = "/oauth2/callback/google")
    public ResponseEntity<?> oauthCallback(@RequestParam String code) {
        final JSONObject body = new JSONObject();
        body.put("code", code);
        body.put("client_id", "90019108061-tg6t5kbfsk9r96k6m3kfle6di458ssiq.apps.googleusercontent.com");
        body.put("client_secret", "aIwt81aPKG9on-bb1phSULQX");
        body.put("grant_type", "authorization_code");
        body.put("redirect_uri", "http://localhost:8080/oauth2/callback/google");

        final ResponseEntity<String> stringResponseEntity = new RestTemplate().postForEntity(
                "https://oauth2.googleapis.com/token",
                body.toString(),
                String.class);


        final JSONObject json = new JSONObject(stringResponseEntity.getBody());
        final Object token = json.get("access_token");

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(
                "https://www.googleapis.com/oauth2/v1/userinfo?alt=json",
                HttpMethod.GET,
                entity,
                String.class);

        return ResponseEntity.ok()
                             .body("your information: ====================\n" + response.getBody());
    }
}
