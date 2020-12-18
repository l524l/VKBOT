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
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import ru.mityushin.responder.config.VKGroupActor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        vk.setVersion("5.126");
    }

    public void sendPhoto(Message message, File image) throws ClientException, ApiException {
        message.setRandomId(new Random().nextInt());
        GetMessagesUploadServerResponse response = vk.photos().getMessagesUploadServer(vkGroupActor.getGroupActor()).execute();
        /*UrlResource resource = null;
        try {
            resource = new UrlResource("classpath:paterns/promo.png");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        File file = null;
        try {
            file = File.createTempFile("as234234",".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(file)) {
            IOUtils.copy(resource.getInputStream(), out);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        MessageUploadResponse mphoto = vk.upload().photoMessage(response.getUploadUrl().toString(), image).execute();
        List<SaveMessagesPhotoResponse> photoQuery = vk.photos().saveMessagesPhoto(vkGroupActor.getGroupActor(), mphoto.getPhoto()).hash(mphoto.getHash()).server(mphoto.getServer()).execute();


        String attach = "photo" + photoQuery.get(0).getOwnerId() +"_"+ photoQuery.get(0).getId();
        vk.messages().send(vkGroupActor.getGroupActor()).randomId(new Random().nextInt()).peerId(message.getPeerId()).attachment(attach).execute();
    }

    public void send(Message message) throws ClientException, ApiException {
        message.setRandomId(new Random().nextInt());
        vk.messages().send(vkGroupActor.getGroupActor()).message(message.getText()).randomId(new Random().nextInt()).peerId(message.getPeerId()).execute();
    }
}
