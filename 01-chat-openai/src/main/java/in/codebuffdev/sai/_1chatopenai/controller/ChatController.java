package in.codebuffdev.sai._1chatopenai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("/ai")
@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chatagain")
    public ResponseEntity<String> chatAgain(@RequestParam(value = "q") String q) {
        String response = chatClient.prompt().user(q).call().content();
        return ResponseEntity.ok(response);
    }

    // Accepts the prompt as a query parameter.
    // If no prompt is provided, uses the default message.
    // The prompt is explicitly treated as a USER message.
    @GetMapping("/chat")
    public String chat(@RequestParam(defaultValue = "Say hello in one sentence") String userPrompt) {
        return chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();
    }

    // Accepts the prompt as JSON in the request body.
    // The prompt is passed directly to prompt(), which is treated as a USER prompt.
    // Returns the AI response wrapped inside an Output record.
    @PostMapping("/chat")
    public Output chatAgain(@RequestBody @Valid Input input) {
        String content = chatClient.prompt(input.prompt()).call().content();
        return new Output(content);
    }

    public record Input(@NotNull String prompt) {
    }

    public record Output(String content) {
    }

    // STREAMING RESPONSE
    // Returns the LLM response incrementally as a Flux.
    // Instead of waiting for the complete response, the client
    // can receive the generated content as it becomes available.
    @GetMapping("/stream")
    public Flux<String> streamingChat(@RequestParam(value = "prompt", defaultValue = "Lyrics of I'm not afraid by Eminem") String prompt) {
        return chatClient.prompt()
                .user(prompt)
//                .call()
                .stream() // in place of a direct call we are asking to stream the response
                .content();
    }


}

