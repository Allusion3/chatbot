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
    public SimpleChatResponse chat(@RequestBody ChatRequest request) {
        return openAiService.getReply(request.getMessage());
    }

}
