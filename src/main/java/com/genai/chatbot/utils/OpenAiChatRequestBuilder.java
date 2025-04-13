package com.genai.chatbot.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenAiChatRequestBuilder {

    public static JSONObject buildChatRequest(String message) {

        // System message that sets the assistant's role
        JSONObject systemMessage = new JSONObject()
                .put("role", "system")
                .put("content", "You are a helpful assistant.");

        // User message from the client
        JSONObject userMessage = new JSONObject()
                .put("role", "user")
                .put("content", message);

        // Messages array required by OpenAI's chat model
        JSONArray messages = new JSONArray()
                .put(systemMessage)
                .put(userMessage);

        // Build the full request payload
        return new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("messages", messages);


    }

}
