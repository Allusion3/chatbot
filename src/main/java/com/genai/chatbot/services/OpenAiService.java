package com.genai.chatbot.services;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String openAiKey;

    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    public String getReply(String message){

        OkHttpClient client = new OkHttpClient();

        String bodyJson = """
            {
              "model": "gpt-3.5-turbo",
              "messages": [
                { "role": "system", "content": "You are a helpful car detailing assistant." },
                { "role": "user", "content": "%s" }
              ]
            }
            """.formatted(message);

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .header("Authorization", "Bearer " + openAiKey)
                .post(RequestBody.create(
                        bodyJson,
                        MediaType.get("application/json; charset=utf-8")
                ))
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "Empty response";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

    }

}
