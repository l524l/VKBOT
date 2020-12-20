package com.l524l.vkcheckersbot.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.l524l.vkcheckersbot.config.VKGroupActor;
import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.objects.callback.ConfirmationMessage;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.l524l.vkcheckersbot.commandsmodule.Commander;

@Service
public class CallbackApiHandler extends CallbackApi {
    private final VKGroupActor actor;
    private final Commander commander;
    private final Gson gson;

    @Autowired
    public CallbackApiHandler(VKGroupActor actor, Commander commander) {
        this.actor = actor;
        this.commander = commander;
        this.gson = new Gson();
    }

    @Override
    public void messageNew(Integer groupId, String secret, Message message) {
        if (secretIsValid(secret)){
            commander.execute(message);
        }
    }

    private boolean secretIsValid(String secret){
        return secret.equals(actor.getSecret());
    }

    @Override
    public boolean parse(String json) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        return parse(jsonObject);
    }


    @Override
    public boolean parse(JsonObject json) {
        String type = json.get("type").getAsString();
        String CALLBACK_EVENT_CONFIRMATION = "confirmation";
        String CALLBACK_EVENT_MESSAGE_NEW = "message_new";
        if (type.equalsIgnoreCase(CALLBACK_EVENT_CONFIRMATION)) {
            ConfirmationMessage message = gson.fromJson(json, ConfirmationMessage.class);
            confirmation(message.getGroupId(), message.getSecret());
            return true;
        }else if (type.equalsIgnoreCase(CALLBACK_EVENT_MESSAGE_NEW)){
            int groupId = json.get("group_id").getAsInt();
            String s = json.get("secret").getAsString();
            Message message =  gson.fromJson(json.get("object").getAsJsonObject().get("message"),Message.class);
            messageNew(groupId, s, message);
            return true;
        }
        return false;
    }
}
