package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogProbResult {

    private  List<String> tokens;
    @JsonProperty("token_logprobs")
    private  List<Double> tokenLogprobs;
    @JsonProperty("top_logprobs")
    private List<Map<String, Double>> topLogprobs;
    private List<Integer> textOffset;

}
