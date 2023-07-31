package service;

import model.ChatGptRequest;
import model.CompletionResult;
import model.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import util.UtilProperties;

import java.util.List;


public class ServiceGpt4ApiClientRestTemplate {

    private final RestTemplate restTemplate;
    private final String url;

    public ServiceGpt4ApiClientRestTemplate() {
        this.url = UtilProperties.getInstance().getProperties("app.uri.gpt4.azure.url");
        this.restTemplate = new RestTemplate();
    }

    public CompletionResult getChatCompletion(List<Message> messages) {
        ChatGptRequest request = new ChatGptRequest(messages,
                Double.parseDouble(UtilProperties.getInstance().getProperties("app.gpt4.temperatura")),
                Integer.parseInt(UtilProperties.getInstance().getProperties("app.gpt4.maxtokens")));

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set("api-key", UtilProperties.getInstance().getProperties("app.key.gpt4.azure"));

        HttpEntity<ChatGptRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<CompletionResult> result = restTemplate.postForEntity(url, httpEntity, CompletionResult.class);

        return result.getBody();

    }
}
