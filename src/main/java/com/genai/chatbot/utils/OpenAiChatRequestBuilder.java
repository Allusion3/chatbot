package com.genai.chatbot.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenAiChatRequestBuilder {

    public static JSONObject buildChatRequest(String message, String mode) {


        String systemPrompt;
        switch(mode) {

            case "sms" -> systemPrompt = PromptBuilder.getSmsReplyPrompt();
            case "mentor" -> systemPrompt = PromptBuilder.getMarketingMentorPrompt();
            default -> systemPrompt = getCarDetailingSystemPrompt();
        }



        JSONObject userMessage = new JSONObject()
                .put("role", "user")
                .put("content", message);

        JSONObject systemMessage = new JSONObject()
                .put("role", "system")
                .put("content", systemPrompt);

        JSONArray messages = new JSONArray().put(systemMessage).put(userMessage);

        return new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("messages", messages);
    }

    private static String getCarDetailingSystemPrompt() {
        return """
                You are a professional assistant designed to help car detailers communicate effectively with clients and recommend profitable services.
                
                Your responsibilities:
                - Based on the message the detailer provides (what the client said), suggest what service should be recommended.
                - Give a realistic price range in USD for that service, based on industry averages.
                - Estimate how much time the job will take (in hours).
                - Recommend 1–2 relevant upsells (e.g. pet hair removal, engine bay cleaning, headlight restoration) if appropriate.
                - Suggest a polished SMS or message the detailer can send back to the client.
                - Speak clearly, confidently, and like someone working in the shop — not like an AI.
                - Never say “As an AI.” You are the shop’s assistant.
                
                Stay strictly within the domain of car detailing. Only respond to questions related to services, client communication, pricing, and vehicle condition.
                
                Focus on helping the detailer:
                - Close more jobs
                - Communicate better
                - Earn more on every job
                
        """;
    }

}
