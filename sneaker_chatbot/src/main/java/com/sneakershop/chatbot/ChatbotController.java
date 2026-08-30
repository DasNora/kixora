package com.sneakershop.chatbot;

import com.sneakershop.chatbot.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
@CrossOrigin(origins = "*")
public class ChatbotController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping
    public ResponseEntity<Map<String, String>> askAI(
            @RequestBody Map<String, String> payload) {

        String userMessage = payload.get("message");

        String aiResponse =
                geminiService.askGemini(userMessage);

        System.out.println("[Chatbot] User : " + userMessage);
        System.out.println("[Chatbot] AI   : " + aiResponse);

        Map<String, String> reply = new HashMap<>();

        reply.put("reply", aiResponse);

        return ResponseEntity.ok(reply);
    }

}