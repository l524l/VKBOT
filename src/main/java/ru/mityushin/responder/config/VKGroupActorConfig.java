package ru.mityushin.responder.config;

import com.vk.api.sdk.client.actors.GroupActor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VKGroupActorConfig {
    @Bean
    public VKGroupActor groupActor(){
        return new VKGroupActor();
    }
}
