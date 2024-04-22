//package com.emtech.Litigation.services.impl;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//public class AuthenticationService {
//    public String authenticateUser(String username, String password) {
//        // Updated login URL to use the ngrok forwarding URL for the authentication service
//        String loginUrl = "https://c365-102-210-244-74.ngrok-free.app/api/v1/auth/signin";
//
//        // Create a request body with the user's credentials
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("username", username);
//        requestBody.put("password", password);
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, entity, String.class);
//
//        String jwtToken = response.getBody();
//
//        return jwtToken;
//    }
//
//    public ResponseEntity<String> accessProtectedResource(String jwtToken) {
//        //updated to the correct endpoint you wish to access
//        String protectedUrl = "https://4415-102-210-244-74.ngrok-free.app/protected-resource";
//
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + jwtToken);
//
//        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(protectedUrl, HttpMethod.GET, entity, String.class);
//
//        return response;
//    }
//
//    public void storeJwtToken(String jwtToken, HttpServletResponse response) {
//        Cookie jwtCookie = new Cookie("JWT-TOKEN", jwtToken);
//        jwtCookie.setHttpOnly(true);
//        jwtCookie.setPath("/");
//        jwtCookie.setMaxAge(60 * 60 * 24); // 24 hours
//
//        response.addCookie(jwtCookie);
//    }
//}
