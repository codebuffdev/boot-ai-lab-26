# spring-ai

1. Helps developers to integrate AI models in Spring Based apps.
2. Enabling AI in spring based apps.
3. Call AI models just like REST APIs in Spring.
4. Without changing much code plug different providers .
5. Work with AI concepts like prompts, embeddings, RAG (Retrieval Augmented Generation), chat history in a structured
   way.

## features

1. Multi-Model & Provider Support
2. Spring friendly APIs
3. Inject and call AI like a normal service.
4. Prompt management
    1. Can store prompts in external files `.st`, `.mustache`,`ftl`) instead of hardcoding.
    2. Makes prompts reusable and version-controlled.
5. Embeddings Support (Vector DB + RAG)
    1. Generate embeddings from text using A1 models.
    2. Store in vector databases like PostgreSQL, Redis, Milvus, Pinecone.
    3. Enable RAG (Retrieval-Augmented Generation) for smarter Q&A.
6. Chat History & Memory
    1. Supports conversational memory ⇒ remembers past interactions.
    2. Useful for chatbots, customer support apps, assistants
7. Streaming Responses
    1. Stream tokens as they are generated (like ChatGPT live typing).
8. Integration with Spring Ecosystem

## ChatClient

1. ChatClient is a helper class that makes it super easy to talk to Ai models.
2. It hides writing raw HTTP requests, handling tokens, parsing JSON.
3. You just call its methods like a normal Spring service, and it gives you Ai responses.
4. It provides Fluent api
5. Supports both synchronous and reactive.
6. Prompt Building (instructions you give to the Ai)
7. Types of messages
    1. User message
    2. System message
8. Dynamic Placeholders
9. Prompt options
10. adjust temperature

## ChatModel

1. Represents a specific chat model provider (e.g., OpenAI, Azure, Hugging Face).
2. Defines the core contract: given some messages, return model output.
3. Unless you need fine-grained control usually don't use ChatModel directly
4. Each vendor has its own implementation:
    1. OpenAiChatModel
    2. AzureOpenAiChatModel
    3. OllamaChatModeI

## ChatClient Internal working 

Controller → ChatClient → ChatModel → LLM

### How OpenAiChatModel created ?

1. We add the starter
   ```groovy
   implementation 'org.springframework.ai:spring-ai-starter-model-openai'
   ```
2. Spring Boot then runs an auto-config.
3. if `spring.ai.model.chat=openai` is configured, OpenAiChatAutoConfiguration is loaded & creates a singleton OpenAiChatModel bean.
4. So ChatClient will have a ChatModel.


## Prompt
```
Prompt
│
├── messages
│     │
│     ├── SystemMessage
│     ├── UserMessage
│     ├── AssistantMessage
│
└── List<Message>
      ├── [0] SystemMessage
      ├── [1] UserMessage
      ├── [2] AssistantMessage
      └── [3] UserMessage
|
└── ChatOptions
      │
      ├── model
      ├── temperature
      ├── maxTokens
      ├── topP
      └── other options
```