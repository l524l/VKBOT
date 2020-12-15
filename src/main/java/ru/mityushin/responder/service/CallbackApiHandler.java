package ru.mityushin.responder.service;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallbackApiHandler extends CallbackApi {
    private final VkMessageSenderService vkMessageSenderService;

    @Autowired
    public CallbackApiHandler(VkMessageSenderService vkMessageSenderService) {
        this.vkMessageSenderService = vkMessageSenderService;
    }

    @Override
    public void messageNew(Integer groupId, Message message) {
        System.out.println(message.getBody());
    }
}
