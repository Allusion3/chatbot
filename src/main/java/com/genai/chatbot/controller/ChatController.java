package com.genai.chatbot.controller;

import com.genai.chatbot.model.ChatRequest;
import com.genai.chatbot.model.SimpleChatResponse;
import com.genai.chatbot.services.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private OpenAiService openAiService;

    @PostMapping
    public Map<String, String> chat(@RequestBody ChatRequest request) {
        SimpleChatResponse reply = openAiService.getReply(request.getMessage(), request.getMode());
        return Map.of("response", reply.getContent());
    }

}
