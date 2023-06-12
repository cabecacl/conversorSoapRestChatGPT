package service;

import com.theokanning.openai.OpenAiService;

public class ServiceChatGPT {
    private static final String CHAT_API_KEY = "sk-bHf0AtCnc5155OwL7vhGT3BlbkFJiZ7CbSRjmKJfD8bRuN";

    public OpenAiService service;
    public ServiceChatGPT(){
        service = new OpenAiService(CHAT_API_KEY);
    }

}
