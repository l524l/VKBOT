package ru.mityushin.responder.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.stereotype.Service;
import ru.mityushin.responder.config.VKGroupActor;

import java.util.Random;

/**
 * Message sender service which work with VK API
 *
 * @author Dmitry Mityushin
 * @since 1.0
 */
@Service
public class VkMessageSenderService {
    private final VKGroupActor vkGroupActor;
    private VkApiClient vk;

    public VkMessageSenderService(VKGroupActor vkGroupActor) {
        this.vkGroupActor = vkGroupActor;
    }

    public void send(Message message) throws ClientException, ApiException {
        message.setRandomId(new Random().nextInt());
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = vk = new VkApiClient(transportClient);
        vk.messages().send(vkGroupActor.getGroupActor()).message(message.getText()).randomId(new Random().nextInt()).peerId(message.getPeerId()).execute();
    }
}
