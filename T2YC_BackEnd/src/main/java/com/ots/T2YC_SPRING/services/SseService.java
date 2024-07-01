package com.ots.T2YC_SPRING.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(String topic, SseEmitter emitter) {
        emitters.put(topic, emitter);
        emitter.onCompletion(() -> emitters.remove(topic));
        emitter.onTimeout(() -> emitters.remove(topic));
    }

    public void sendSseMessage(String topic, Object message) {
        SseEmitter emitter = emitters.get(topic);
        if (emitter != null) {
            try {
                emitter.send(message);
            } catch (IOException e) {
                emitters.remove(topic);
            }
        }

    }
    public void removeEmitter(String topic, SseEmitter emitter) {
        emitters.remove(topic);

    }
}
