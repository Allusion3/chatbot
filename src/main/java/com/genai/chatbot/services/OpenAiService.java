package com.genai.chatbot.services;

import com.genai.chatbot.model.SimpleChatResponse;
import com.genai.chatbot.utils.OpenAiChatRequestBuilder;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String openAiKey;

    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    public SimpleChatResponse getReply(String message) {
        OkHttpClient client = new OkHttpClient();

        // Build JSON payload using your util class
        JSONObject payload = OpenAiChatRequestBuilder.buildChatRequest(message);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + openAiKey)
                .post(RequestBody.create(
                        payload.toString(),
                        MediaType.get("application/json; charset=utf-8")
                ))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);

                // Pull the content from the assistant
                String content = json
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");

                // Get token usage stats
                JSONObject usage = json.getJSONObject("usage");
                int prompt = usage.getInt("prompt_tokens");
                int completion = usage.getInt("completion_tokens");
                int total = usage.getInt("total_tokens");

                // ✅ This is your clean return object
                return new SimpleChatResponse(content, prompt, completion, total);
            }
        } catch (Exception e) {
            return new SimpleChatResponse("Error: " + e.getMessage(), 0, 0, 0);
        }

        return new SimpleChatResponse("No response from OpenAI.", 0, 0, 0);

    }

}
