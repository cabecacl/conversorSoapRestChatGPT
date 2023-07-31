package model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatGptRequest {

    private List<Message> messages = new ArrayList<Message>();
    private int max_tokens;
    private double temperature;

    public ChatGptRequest(@JsonProperty("messages") List<Message> messages,
                          @JsonProperty("temperature") double temperature,
                          @JsonProperty("max_tokens") int max_tokens) {
        this.messages = messages;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }

}
