package com.ots.T2YC_SPRING.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ots.T2YC_SPRING.mqtt.MqttGateway;
import com.ots.T2YC_SPRING.services.SseService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    @Value("${secret.key}")
    private String SECRET_KEY;

    private final MqttGateway mqttGateway;
    private final SseService sseService;

    public MqttController(MqttGateway mqttGateway, SseService sseService) {
        this.mqttGateway = mqttGateway;
        this.sseService = sseService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> publish(@RequestBody String mqttMessage) {
        try {
            // Use Gson to parse the message
            if (mqttMessage != null) {
                JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);

                // Extract the message content and topic
                String messageContent = convertObject.get("message").toString();
                String topic = convertObject.get("topic").getAsString();

                mqttGateway.senToMqtt(messageContent, topic);
                return ResponseEntity.ok("Success");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null");
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam String topic, @RequestParam String token) {
        if(!isTokenValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addEmitter(topic, emitter);
        emitter.onCompletion(() -> sseService.removeEmitter(topic, emitter));
        emitter.onTimeout(() -> sseService.removeEmitter(topic, emitter));
        emitter.onError((e) -> sseService.removeEmitter(topic, emitter));
        return emitter;
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT");
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT");
        } catch (SecurityException e) {
            System.out.println("Invalid signature");
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument token");
        } catch (JwtException e) {
            System.out.println("Invalid token");
        }
        return false;
    }
}

