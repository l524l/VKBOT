package ru.mityushin.responder.service;

import com.vk.api.sdk.actions.Upload;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.GetMessagesUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.MessageUploadResponse;
import com.vk.api.sdk.objects.photos.responses.SaveMessagesPhotoResponse;
import com.vk.api.sdk.queries.photos.PhotosGetMessagesUploadServerQuery;
import com.vk.api.sdk.queries.photos.PhotosSaveMessagesPhotoQuery;
import com.vk.api.sdk.queries.upload.UploadPhotoMessageQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import ru.mityushin.responder.config.VKGroupActor;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = vk = new VkApiClient(transportClient);
        message.setRandomId(new Random().nextInt());
        GetMessagesUploadServerResponse response = vk.photos().getMessagesUploadServer(vkGroupActor.getGroupActor()).execute();
        ApplicationContext context = new ClassPathXmlApplicationContext();
        File file = null;
        try {
            file = context.getResource("classpath:paterns/promo.png").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageUploadResponse mphoto = vk.upload().photoMessage(response.getUploadUrl().toString(), file).execute();
        List<SaveMessagesPhotoResponse> photoQuery = vk.photos().saveMessagesPhoto(vkGroupActor.getGroupActor(), mphoto.getPhoto()).hash(mphoto.getHash()).server(mphoto.getServer()).execute();
        vk.setVersion("5.126");

        String attach = "photo" + photoQuery.get(0).getOwnerId() +"_"+ photoQuery.get(0).getId();
        vk.messages().send(vkGroupActor.getGroupActor()).message(message.getText()).randomId(new Random().nextInt()).peerId(message.getPeerId()).attachment(attach).execute();
    }
}
