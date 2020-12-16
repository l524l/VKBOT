package ru.mityushin.responder.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.ConfirmationMessage;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mityushin.responder.commandsmodule.Commander;
import ru.mityushin.responder.controller.CallbackController;

@Service
public class CallbackApiHandler extends CallbackApi {
    private static final Logger LOG = LoggerFactory.getLogger(CallbackController.class);
    private final VkMessageSenderService vkMessageSenderService;
    private final Commander commander;

    @Autowired
    public CallbackApiHandler(VkMessageSenderService vkMessageSenderService, Commander commander) {
        this.vkMessageSenderService = vkMessageSenderService;
        this.commander = commander;
    }

    @Override
    public void messageNew(Integer groupId, Message message) {
        commander.execute(message);
    }

    @Override
    public void confirmation(Integer groupId) {
        Message message1 = new Message();
        super.confirmation(groupId);
        message1.setText("d70aff5a");
        try {
            vkMessageSenderService.send(message1);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }
    Gson gson = new Gson();

    @Override
    public boolean parse(String json) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        return parse(jsonObject);
    }

    private static final String CALLBACK_EVENT_CONFIRMATION = "confirmation";
    @Override
    public boolean parse(JsonObject json) {
        String type = json.get("type").getAsString();
        if (type.equalsIgnoreCase(CALLBACK_EVENT_CONFIRMATION)) {
            ConfirmationMessage message = gson.fromJson(json, ConfirmationMessage.class);
            confirmation(message.getGroupId(), message.getSecret());
            return true;
        }else {
            int groupId = json.get("group_id").getAsInt();
            String s = json.get("secret").getAsString();
            Message message =  gson.fromJson(json.get("object").getAsJsonObject().get("message"),Message.class);
            messageNew(groupId, s, message);
            return true;
        }
    }
}
