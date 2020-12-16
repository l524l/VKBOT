package ru.mityushin.responder.service;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mityushin.responder.config.VKGroupActor;
import ru.mityushin.responder.dto.MessagesSendDto;
import ru.mityushin.responder.dto.MessagesSendResultDto;
import ru.mityushin.responder.util.exception.MessageSenderException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Message sender service which work with VK API
 *
 * @author Dmitry Mityushin
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class VkMessageSenderService {
    private static final int TEXT_MAX_LENGTH = 2000;
    private final RestTemplate restTemplate;
    private final VkUriCreator vkUriCreator;
    private final VKGroupActor vkGroupActor;
    private VkApiClient vk;

    public void send(Message message) throws ClientException, ApiException {
        Message messages = new Message();
        message.setRandomId(new Random().nextInt());
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = vk = new VkApiClient(transportClient);
        vk.messages().send(vkGroupActor.getGroupActor()).message(message.getText()).randomId(new Random().nextInt()).peerId(446899878).execute();

       /* List<MessagesSendDto> messages = parseIfRequired(message);
        messages.forEach(this::sendInternal);*/
    }

    private List<MessagesSendDto> parseIfRequired(MessagesSendDto dto) {
        CallbackApi s = new CallbackApi();
        String originalMessage = dto.getMessage();
        int capacity = originalMessage.length() / TEXT_MAX_LENGTH + 1;
        ArrayList<MessagesSendDto> result = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            int beginIndex = i * TEXT_MAX_LENGTH;
            int endIndex = Math.min((i + 1) * TEXT_MAX_LENGTH, originalMessage.length());
            result.add(copyWithNewMessage(dto, originalMessage.substring(beginIndex, endIndex)));
        }
        return result;
    }

    private static MessagesSendDto copyWithNewMessage(MessagesSendDto dto, String message) {
        return MessagesSendDto.builder()
                .userId(dto.getUserId())
                .peerId(dto.getPeerId())
                .domain(dto.getDomain())
                .chatId(dto.getChatId())
                .userIds(dto.getUserIds())
                .message(message)
                .lat(dto.getLat())
                .longField(dto.getLongField())
                .attachment(dto.getAttachment())
                .replyTo(dto.getReplyTo())
                .forwardMessages(dto.getForwardMessages())
                .stickerId(dto.getStickerId())
                .groupId(dto.getGroupId())
                .keyboard(dto.getKeyboard())
                .payload(dto.getPayload())
                .dontParseLinks(dto.getDontParseLinks())
                .build();
    }

    private void sendInternal(MessagesSendDto message) {
        message.setRandomId((long) message.hashCode());
        URI uri = vkUriCreator.createUri(message);
        ResponseEntity<MessagesSendResultDto> responseEntity = restTemplate.postForEntity(uri, null, MessagesSendResultDto.class);
        validateResponse(responseEntity);
    }

    private void validateResponse(ResponseEntity<MessagesSendResultDto> responseEntity) {
        MessagesSendResultDto.MessagesSendErrorResultDto error = Objects.requireNonNull(responseEntity.getBody()).getError();
        if (error != null && error.getErrorCode() != null) {
            throw new MessageSenderException(error.getErrorMsg());
        }
    }
}
