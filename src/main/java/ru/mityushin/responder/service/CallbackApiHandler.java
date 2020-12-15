package ru.mityushin.responder.service;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mityushin.responder.controller.CallbackController;

@Service
public class CallbackApiHandler extends CallbackApi {
    private static final Logger LOG = LoggerFactory.getLogger(CallbackController.class);
    private final VkMessageSenderService vkMessageSenderService;

    @Autowired
    public CallbackApiHandler(VkMessageSenderService vkMessageSenderService) {
        this.vkMessageSenderService = vkMessageSenderService;
    }

    @Override
    public void messageNew(Integer groupId, Message message) {
        LOG.info("MESSAGE: " + message.getText());
        try {
            vkMessageSenderService.send(message);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(message.getText());
    }
}
