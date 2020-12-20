package ru.mityushin.responder.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.responses.GetMessagesUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.MessageUploadResponse;
import com.vk.api.sdk.objects.photos.responses.SaveMessagesPhotoResponse;
import org.springframework.stereotype.Service;
import ru.mityushin.responder.config.VKGroupActor;

import java.io.File;
import java.util.List;
import java.util.Random;

@Service
public class VkMessageSenderService {
    private final VKGroupActor vkGroupActor;
    private VkApiClient vk;

    public VkMessageSenderService(VKGroupActor vkGroupActor) {
        this.vkGroupActor = vkGroupActor;
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        vk.setVersion(vkGroupActor.getV());
    }

    public void sendPhoto(Message message, File image) throws ClientException, ApiException {
        message.setRandomId(new Random().nextInt());
        GetMessagesUploadServerResponse response = vk.photos()
                .getMessagesUploadServer(vkGroupActor.getGroupActor())
                .execute();

        MessageUploadResponse mphoto = vk.upload().
                photoMessage(response.getUploadUrl().toString(), image)
                .execute();

        List<SaveMessagesPhotoResponse> photoQuery = vk.photos()
                .saveMessagesPhoto(vkGroupActor.getGroupActor(), mphoto.getPhoto())
                .hash(mphoto.getHash())
                .server(mphoto.getServer())
                .execute();

        String attach = "photo" + photoQuery.get(0).getOwnerId() +"_"+ photoQuery.get(0).getId();
        vk.messages().send(vkGroupActor.getGroupActor()).randomId(new Random().nextInt())
                .peerId(message.getPeerId())
                .attachment(attach)
                .execute();
    }

    public void send(Message message) throws ClientException, ApiException {
        message.setRandomId(new Random().nextInt());
        vk.messages().send(vkGroupActor.getGroupActor())
                .message(message.getText())
                .randomId(new Random().nextInt())
                .peerId(message.getPeerId())
                .execute();
    }
}
