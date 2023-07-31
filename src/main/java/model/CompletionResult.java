package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompletionResult {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<CompletionChoice> choices;
    private Usage usage;
}
