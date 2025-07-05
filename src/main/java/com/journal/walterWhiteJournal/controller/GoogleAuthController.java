package com.journal.walterWhiteJournal.controller;

import com.journal.walterWhiteJournal.Repository.UserRepository;
import com.journal.walterWhiteJournal.Repository.UserRepositoryImp;
import com.journal.walterWhiteJournal.entity.User;
import com.journal.walterWhiteJournal.service.UserDetailsServiceImp;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/google-auth")
public class GoogleAuthController {
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestBody String code){
        try {
            //Exchange authentication code for access token
            String tokenEndPoint = "https://oauth2.googleapis.com/token";
            // Here you would typically use a RestTemplate or WebClient to make a POST request to the token endpoint
            //HashMap holds the information to be sent in the POST request
            Map<String, String> params = new HashMap<>();
            params.put("code", code);
            String client_id = "";
            String client_secret = "";
            params.put("client_id", client_id);
            params.put("client_secret", client_secret);
            params.put("redirect_uri", "https://developers.google.com/oauthplayground");
            params.put("grant_type", "authorization_code");
            //set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //make the request
            HttpEntity<Map<String,String>> request = new HttpEntity<>(params,headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndPoint,request,Map.class); //last one is response type

            //extract the access token from the response
            String idToken = tokenResponse.getBody().get("id_token").toString();
            //use this toke to hit the Google UserInfo endpoint
            String userInfoEndpoint = "https://oauth2.googleapis.com/tokeninfo?id+token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoEndpoint,Map.class);

            //extract user information
            if(userInfoResponse.getStatusCode() == HttpStatus.OK) {
                String email = userInfoResponse.getBody().get("email").toString();
                String name = userInfoResponse.getBody().get("name").toString();
                UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(email);
                if (userDetails == null) {
                    User user = new User();
                    user.setUsername(name);
                    user.setEmail(email);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    user.setRoles(Arrays.asList("USER"));
                    userRepository.save(user);
                    userDetails = userDetailsServiceImp.loadUserByUsername(name);
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return new ResponseEntity<>("User authenticated successfully", HttpStatus.OK);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
