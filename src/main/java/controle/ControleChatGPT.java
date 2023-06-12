package controle;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import service.ServiceChatGPT;

public class ControleChatGPT {

    private ServiceChatGPT serviceChatGPT;
    public ControleChatGPT(ServiceChatGPT service){
        this.serviceChatGPT = service;
    }

    public CompletionResult request(String texto){
        CompletionRequest request = CompletionRequest.builder()
//                .model("text-davinci-002")
                .model("text-davinci-003")
                .prompt(texto)
                .echo(true)
                .temperature(0.0)
                .maxTokens(900)
                .build();
        return serviceChatGPT.service.createCompletion(request);
    }

}
